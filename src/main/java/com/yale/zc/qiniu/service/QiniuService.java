package com.yale.zc.qiniu.service;

import com.yale.zc.core.config.RedisConfig;
import com.yale.zc.core.dao.JedisClient;
import com.yale.zc.core.util.JsonUtils;
import com.yale.zc.core.util.RandomUtil;
import com.yale.zc.core.util.RespMessage;
import com.yale.zc.qiniu.vo.QiniuCallbackVo;
import com.yale.zc.qiniu.vo.QiniuTokenVo;
import com.yale.zc.qiniu.vo.UploadVo;
import com.yale.zc.qrcode.config.MatrixToBgImageConfig;
import com.yale.zc.qrcode.util.QrcodeUtils;
import com.yale.zc.user.bean.User;
import com.yale.zc.user.bean.UserWx;
import com.yale.zc.user.dao.UserMapper;
import com.yale.zc.user.dao.UserWxMapper;
import com.yale.zc.user.service.UserService;
import com.yale.zc.user.vo.UserRedisVo;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;


/**
 * 七牛service
 * @author GrayFox
 *
 */
@Service
@ConfigurationProperties(prefix = "qiniu")
public class QiniuService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QiniuService.class);

	@Value(value="${qiniu.ak}")
	private String ak;
	
	@Value(value="${qiniu.sk}")
	private String sk;
	
	@Value(value="${qiniu.bucket}")
	private String bucket;
	
	@Value(value="${qiniu.callback}")
	private String callback;

	@Value(value="${qiniu.domain}")
	private String domain;

	@Value(value="${qiniu.body}")
	private String body;

	@Value(value="${qiniu.qrcodeurl}")
	private String qrcodeurl;

	@Value(value="${qiniu.qrcodetemppath}")
	private String qrcodetemppath;



	@Autowired
	UserMapper userMapper;

	@Autowired
	UserWxMapper userWxMapper;

	@Autowired
	UserService userService;

	@Autowired
	JedisClient jedisClient;

	/**
	 * 获取upload token
	 * @return RespMessage
	 */

	public RespMessage token() {
		Auth auth = Auth.create(ak, sk);
		// 生成随机文件名
		String key = UUID.randomUUID().toString();
		StringMap putPolicy = new StringMap();
		putPolicy.put("callbackUrl", callback);
		putPolicy.put("callbackBody", body);
		putPolicy.put("callbackBodyType", "application/json");
		long expireSeconds = 3600;
		String token = auth.uploadToken(bucket, key, expireSeconds, putPolicy);
		QiniuTokenVo vo = new QiniuTokenVo(token, key);

		return RespMessage.success(vo);
	}
	
	/**
	 * 接收七牛服务器回调
	 * @param vo 
	 * @return
	 */
	public RespMessage callback(QiniuCallbackVo vo) {

		return RespMessage.success(vo);		
	}

	@Async
	/**
	 * 异步生成个人名片
	 */
	public void createCardAsync(String token) throws IOException {

		createCardFunction(token);

	}

	public RespMessage createCard(String token) throws IOException {

		UploadVo uploadVo =  new UploadVo();
		uploadVo = createCardFunction(token);

		return RespMessage.success(uploadVo);
	}

	private UploadVo createCardFunction(String token) throws IOException {

		LOGGER.info("##createCard:"+token);
		//Resource resource = new ClassPathResource("/static/bg.png");

		UserRedisVo userRedisVo = userService.getUserByToken(token);
		if (userRedisVo==null){
			//return RespMessage.fail(400, "登录超时");
		}

		UserWx userWx = userRedisVo.getUserWx();
		if ( userWx == null) {
			//return RespMessage.fail("微信用户不存在");
		}
		UploadVo uploadVo =  new UploadVo();
		User user = userMapper.selectByPrimaryKey(userWx.getUserId());
		String qrcodeUrl = user.getQrcodeUrl();

		// if (StringUtils.isNotEmpty(qrcodeUrl)){

		MatrixToBgImageConfig config = new MatrixToBgImageConfig();

		config.setHeadimgUrl(userWx.getHeadImageUrl());
		config.setRealname(userWx.getNickname());
		config.setQrCodeUrl(qrcodeurl);
		config.setUserId(userWx.getUserId());
		config.setTempPath(qrcodetemppath);

		byte[] qrCodeBytes = QrcodeUtils.createQrcode(config);


		String filePath = "E:\\temp\\qrcode_with_bg_1119108274369957252101.jpg";
		//第一种方式: 指定具体的要上传的zone
		//注：该具体指定的方式和以下自动识别的方式选择其一即可
		//要上传的空间(bucket)的存储区域为华东时
		// Zone z = Zone.zone0();
		//要上传的空间(bucket)的存储区域为华北时
		// Zone z = Zone.zone1();
		//要上传的空间(bucket)的存储区域为华南时
		// Zone z = Zone.zone2();

		//第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
		Zone zone = Zone.zone2();

		Configuration cfg = new Configuration(zone);
		UploadManager uploadManager = new UploadManager(cfg);

		//生成token
		Auth auth = Auth.create(ak, sk);
		// 生成随机文件名
		// String key = UUID.randomUUID().toString();
		String key = userWx.getUserId();

		String qiniuToken = auth.uploadToken(bucket, key);

		try {
			Response res = uploadManager.put(qrCodeBytes, key, qiniuToken);
			if (!res.isOK()) {
				throw new RuntimeException("上传七牛出错：" + res.toString());
			}
		} catch (QiniuException e) {
			//return RespMessage.fail(400, "上传二维码出错："+e.response.statusCode);

		}
		String imageUrl =  domain+"/"+key+"?v="+ RandomUtil.getRandom();  //后面带参数?v= 是为了同名覆盖上传后刷新缓存，不带参数还是显示早期的图片
		//更新user表的字段

		user.setQrcodeUrl(imageUrl);
		userMapper.updateByPrimaryKeySelective(user);
		userRedisVo.setUser(user);

		//同步redis
		jedisClient.set(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(userRedisVo));
		// 设置session的过期时间
		jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);

		// }

		uploadVo.setImageUrl(imageUrl);
		LOGGER.info("#imageUrl:"+imageUrl);

		return uploadVo;
	}

}
