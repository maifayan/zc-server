package com.yale.zc.weixin.service;

import com.yale.zc.core.config.RedisConfig;
import com.yale.zc.core.dao.JedisClient;
import com.yale.zc.core.util.JsonUtils;
import com.yale.zc.core.util.WeixinAuthUtil;
import com.yale.zc.core.util.WeixinMessageConverter;
import com.yale.zc.core.util.WeixinUtil;
import com.yale.zc.qiniu.service.QiniuService;
import com.yale.zc.user.bean.User;
import com.yale.zc.user.bean.UserWx;
import com.yale.zc.user.dao.UserMapper;
import com.yale.zc.user.dao.UserWxMapper;
import com.yale.zc.user.vo.UserRedisVo;
import com.yale.zc.weixin.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class WeixinService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinService.class);

	@Autowired
    WeixinAuthUtil weixinAuthUtil;
	
	@Autowired
    UserMapper userMapper;
	
	@Autowired
    UserWxMapper userWxMapper;
	
	@Autowired
    JedisClient jedisClient;

	@Autowired
	QiniuService qiniuService;

	public void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.info("#weixin auth");

		LOGGER.info("#refereeId:"+request.getParameter("userId"));
		String refereeId = request.getParameter("userId")==null?"":request.getParameter("userId");
		 	String url = weixinAuthUtil.getAuthUrl(refereeId);
		response.sendRedirect(url);
	}

	public void callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
	 	String code = request.getParameter("code");

		String refereeId = request.getParameter("refereeId");
		LOGGER.info("#callback refereeId:"+refereeId+"#code:"+code);

		String tokenUrl = weixinAuthUtil.getTokenUrl(code);
		LOGGER.info("tokenUrl: " + tokenUrl);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new WeixinMessageConverter());
		AccessTokenVo accessTokenVo = restTemplate.getForObject(tokenUrl, AccessTokenVo.class);
		LOGGER.info("weixin access token: " + accessTokenVo.getAccess_token());
		if (accessTokenVo.getAccess_token() == null || accessTokenVo.getOpenid() == null) {
			LOGGER.info("weixin access token: fail");
			return;
		}
		//System.out.println(accessTokenVo);
		String openId = accessTokenVo.getOpenid();
		String accessToken = accessTokenVo.getAccess_token();
		String infoUrl = weixinAuthUtil.getInfoUrl(accessToken, openId);
		UserInfoVo userInfoVo = restTemplate.getForObject(infoUrl, UserInfoVo.class);

		if ("".equals(userInfoVo.getOpenid()) || userInfoVo.getOpenid() == null) {
			// return RespMessage.fail(RespMessage.FAIL_INVALID_OPEN_ID);
			LOGGER.info("weixin openid: fail");
			response.sendRedirect("/weixin/auth");
		} else {
			// 判断OPENID是否已注册
			UserWx existUser = userWxMapper.selectByOpenId(userInfoVo.getOpenid());

			if (existUser != null) {
				//System.out.println(existUser);
				String token = UUID.randomUUID().toString();
				LOGGER.info("exist user new token: " + token);
				// 存入redis
				// 保存用户之前，把用户对象中的密码清空。
				User user = userMapper.selectByPrimaryKey(existUser.getUserId());
				user.setPassword(null);

				UserRedisVo userRedisVo = new UserRedisVo();
				userRedisVo.setUser(user);

				// 查询微信信息
				userRedisVo.setUserWx(existUser);

				// 把用户信息写入redis
				jedisClient.set(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(userRedisVo));
				// 设置session的过期时间
				jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);

				////异步生成个人名片并上传七牛
			//	qiniuService.createCardAsync(token);

				response.sendRedirect(weixinAuthUtil.oauthRedirectUrl + token);
				// response.sendRedirect("/upload_async.html");
			} else {
				
				// 新建user
				User user = new User();
				String userId = UUID.randomUUID().toString();
				user.setId(userId);
				if (!StringUtils.isEmpty(refereeId)){
					user.setRefereeId(refereeId);     //推荐人ID
				}

				// 新建user_wx
				UserWx userWx = new UserWx();
				String id = UUID.randomUUID().toString();

				userWx.setId(id);
				userWx.setUserId(userId);
				userWx.setCity(userInfoVo.getCity());
				userWx.setCountry(userInfoVo.getCountry());
				userWx.setHeadImageUrl(userInfoVo.getHeadimgurl());
				userWx.setLanguage(userInfoVo.getLanguage());
				userWx.setNickname(userInfoVo.getNickname());
				userWx.setOpenid(userInfoVo.getOpenid());
				// user.setPrivilege(userInfoVo.getPrivilege());
				userWx.setProvince(userInfoVo.getProvince());
				userWx.setSex(Integer.parseInt(userInfoVo.getSex()));
				userWx.setUnionid(userInfoVo.getUnionid());

				// 写入数据
				userMapper.insertSelective(user);
				int result = userWxMapper.insertSelective(userWx);

				if (result > 0) {
					/*
					session.setAttribute("currentUser", userWx);
					session.setAttribute("loginIp", IpUtil.getIpAddr(request));
					*/
					// 生成token
					// 保存到redis
					// 生成token
					String token = UUID.randomUUID().toString();
//					System.out.println("new user new token: " + token);
					// 存入redis
					// 保存用户之前，把用户对象中的密码清空。
					user.setPassword(null);

					UserRedisVo userRedisVo = new UserRedisVo();
					userRedisVo.setUser(user);

					// 查询微信信息
					userRedisVo.setUserWx(userWx);

					// 把用户信息写入redis
					jedisClient.set(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(userRedisVo));
					// 设置session的过期时间
					jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);

				//	//异步生成个人名片并上传七牛
				//	qiniuService.createCardAsync(token);

					// CookieUtils.setCookie(request, response, "OPEN_ID", user.getOpenId(), 7200);
					response.sendRedirect(weixinAuthUtil.oauthRedirectUrl + token);
				} else {
					response.sendRedirect("/weixin/auth");
				}
			}
		}
	}

	public String config(WeixinSignVo vo) {
		
		if (vo.getSignature() == null || vo.getTimestamp() == null || vo.getNonce() == null) {
			return null;
		}

		if (WeixinUtil.checkSignature(vo.getSignature(), vo.getTimestamp(), vo.getNonce())) {
			return vo.getEchostr();
		} else {
			return null;
		}
	}


	public Map<String, Object> wxShareConfig(ServletRequest request) {

		String jsapi_ticket ;
		//获取Unix时间戳
		long timestamp = System.currentTimeMillis() / 1000L;
		//WxJsUtils wxJsUtils = new WxJsUtils();

		//获取appId
		//获取页面路径(前端获取时采用location.href.split('#')[0]获取url)
		String url = request.getParameter("url");
		String userId = request.getParameter("userId");
		String from = request.getParameter("from");
		if (!StringUtils.isEmpty(userId)){
			url+="&userId="+userId;
		}
		if (!StringUtils.isEmpty(from)){
			url+="&from="+from;
		}
		System.out.println("#### share page url:"+url);
		//判断ticket是否有效
		String ticket = jedisClient.get("WEIXIN_JSSDK_TICKET");
		if(!StringUtils.isEmpty(ticket)){//从缓存中获取ticket
			//System.out.println("#########从缓存中获取ticket:" +ticket);
			jsapi_ticket = ticket;
		}else {
			//System.out.println("#########直接获取ticket" );
			//获取access_token
			String accessTokenUrl = weixinAuthUtil.getShareAccessTokenUrl();
			LOGGER.info("#accessTokenUrl:"+accessTokenUrl);
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new WeixinMessageConverter());
			ShareAccessToken accessToken = restTemplate.getForObject(accessTokenUrl, ShareAccessToken.class);
			LOGGER.info("#accessToken:"+accessToken.getAccess_token());
			// 插入缓存
			jedisClient.set("WEIXIN_ACCESS_TOKEN" , accessToken.getAccess_token());
			jedisClient.expire("WEIXIN_ACCESS_TOKEN" , accessToken.getExpires_in());

			//获取ticket
			String ticketUrl = weixinAuthUtil.getTicketUrl(accessToken.getAccess_token());
		 	restTemplate.getMessageConverters().add(new WeixinMessageConverter());
			ShareTicket shareTicket = restTemplate.getForObject(ticketUrl, ShareTicket.class);
			jsapi_ticket = shareTicket.getTicket();
			//System.out.println("#########直接获取ticket:" +jsapi_ticket);
			if (!StringUtils.isEmpty(jsapi_ticket)) {
				jedisClient.set("WEIXIN_JSSDK_TICKET", jsapi_ticket);
				jedisClient.expire("WEIXIN_JSSDK_TICKET", shareTicket.getExpires_in());
			}

		 }
		String noncestr = UUID.randomUUID().toString();
		//获取签名
		String signature = WeixinUtil.signature(jsapi_ticket, timestamp, noncestr, url);
		//System.out.println("####signature:"+signature);
		//创建Map用于创建签名串
		Map<String, Object> params = new HashMap<>();
		params.put("jsapi_ticket", jsapi_ticket);
		params.put("timestamp", timestamp);
		params.put("noncestr", noncestr);
		//params.put("url", url);

		//得到签名再组装到Map里
		params.put("signature", signature);
		//传入对应的appId
		params.put("appId", weixinAuthUtil.getAppId());
	//	params.put("appId", "wx762a7aeec7281d41");   //victor
		return params;
	}

}
