package com.yale.zc.user.web;

import com.yale.zc.core.config.RedisConfig;
import com.yale.zc.core.dao.JedisClient;
import com.yale.zc.core.util.JsonUtils;
import com.yale.zc.core.util.RespMessage;
import com.yale.zc.system.bean.SysMessageToUser;
import com.yale.zc.user.bean.User;
import com.yale.zc.user.bean.UserDetail;
import com.yale.zc.user.dao.UserMapper;
import com.yale.zc.user.service.UserService;
import com.yale.zc.user.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户帐号相关业务
 * @author GrayFox on 20170619
 *
 */

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserMapper userMapper;

	@Autowired
	JedisClient jedisClient;


	/**
	 * 用户使用手机号密码登录
	 * @param vo
	 * @return
	 */
	@PostMapping("/sign-in")
	public RespMessage signIn(SignInVo vo, String token) {
		return userService.signIn(vo, token);
	}

	/**
	 * 短信验证登录
	 * @param vo
	 * @return
	 */
	@PostMapping("/sign-in-sms")
	public RespMessage signInSms(SignInSmsVo vo, String token) {
		return userService.signInSms(vo, token);
	}

	/**
	 * 登出
	 * @return
	 */
	@PostMapping("/sign-out")
	public RespMessage signOut(String token) {
		return userService.signOut(token);
	}

	/**
	 * 请求发送验证码短信
	 * @param phone
	 * @return
	 */
	@PostMapping("/send-sms")
	public RespMessage sendSms(String phone) {
//		System.out.println("/send-sms to:" + phone);
		return userService.sendSms(phone);
	}

	/**
	 * token换取用户信息
	 * @param token
	 * @return
	 */
	@PostMapping("/token")
	public RespMessage getUserByToken(String token) {
//		System.out.println("/user/token" + token);
		UserRedisVo result = userService.getUserByToken(token);
		if (result == null) {
			return RespMessage.fail(400, "此session已经过期，请重新登录");
		}
		// 刷新用户信息
		User user = userMapper.selectByPrimaryKey(result.getUser().getId());
		result.setUser(user);
		// 把用户信息写入redis
		jedisClient.set(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(result));
		// 设置session的过期时间
		jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);



		return RespMessage.success(result);
	}

	/**
	 * 补充注册信息，关联手机号
	 * @param vo
	 * @param token
	 * @return
	 */
	@PostMapping("/sign-up")
	public RespMessage signUp(SignUpVo vo, String token) {
		return userService.signUp(vo, token);
	}

	/**
	 * 手机浏览器（非微信内置浏览器）注册信息
	 * @param vo
	 * @param
	 * @return
	 */
	@PostMapping("/web-sign-up")
	public RespMessage webSignUp(SignUpVo vo) {
		return userService.webSignUp(vo);
	}

	/**
	 * 开放平台预注册信息
	 * @param vo
	 * @return
	 */
	@PostMapping("/pre-sign-up")
	public RespMessage preSignUp(UserWxIOSVo vo) {
		return userService.preSignUp(vo);
	}

	/**
	 * 重置密码
	 * @param vo
	 * @param token
	 * @return
	 */
	@PostMapping("/reset-password")
	public RespMessage resetPassword(ResetPasswordVo vo, String token) {
		return userService.resetPassword(vo, token);
	}

	/**
	 * 实名认证
	 * @param vo
	 * @param token
	 * @return
	 */
	@PostMapping("/realname-cert")
	public RespMessage realnameCert(RealNameCertVo vo, String token) {
		return userService.realnameCert(vo, token);
	}

	/**
	 * 查询实名认证的进度
	 */
	@PostMapping("/list-my-cert-log")
	public RespMessage selectRealnameCert(String token){ return userService.selectRealnameCert(token);}


	/**
	 * 补填银行信息
	 * @param vo
	 * @param token
	 * @return
	 */
	@PostMapping("/add-bank-detail")
	public RespMessage addBankDetail(UserDetail vo, String token) {
		return userService.addBankDetail(vo, token);
	}



	/**
	 * 修改个人信息
	 * @param userWxVo
	 * @param token
	 */
	@PostMapping("/update-wx")
	public RespMessage updateUserWx(UserWxVo userWxVo,String token){ return userService.updateUserWx(userWxVo, token); }

	/**
	 * 修改银行信息
	 * @param userDetailVo
	 * @param token
	 */
	@PostMapping("/update-bank-detail")
	public RespMessage updateBank(UserDetailVo userDetailVo, String token){ return userService.updateBank(userDetailVo, token); }

	/**
	 * 查询用户信息
	 * @param id
	 */
	@PostMapping("/query-by-user-id")
	public RespMessage queryByUserId(String id){ return userService.queryByUserId(id); }

	/**
	 <<<<<<< Updated upstream
	 * 系统消息通知
	 =======
	 * 系统退款消息通知
	 >>>>>>> Stashed changes
	 * @param userId
	 * @return
	 */
	@PostMapping("/sys-message-receive")
	public RespMessage sysMessageReceive(String userId){
		return userService.selectByUserId(userId);
	}

	/**
	 * 删除退款消息
	 * @param id
	 * @return
	 */
	@PostMapping("/delete-message")
	public RespMessage deleteMessage(String id, String token){
		return userService.deleteMessage(id, token);
	}


	/**
	 * 查询银行卡信息
	 * @param userId
	 * @param token
	 * @return
	 */
	@PostMapping("/query-bank-detail")
	public RespMessage queryUserDetail(String userId, String token){
		return userService.queryUserDetail(userId, token);
	}

	/**
	 * 修改个人信息(修改头像，昵称，性别)
	 * @param vo
	 * @param token
	 */
	@PostMapping("/update-user")
	public RespMessage updateUser(User vo, String token){

		return userService.updateUser(vo, token); }

}
