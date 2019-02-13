package com.yale.zc.user.service;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.user.bean.User;
import com.yale.zc.user.bean.UserDetail;
import com.yale.zc.user.vo.*;


public interface UserService {

	UserRedisVo getUserByToken(String token);
	
	RespMessage signIn(SignInVo vo, String token);
	
	RespMessage signOut(String token);

	RespMessage sendSms(String phone);

	RespMessage signInSms(SignInSmsVo vo, String token);

	RespMessage signUp(SignUpVo vo, String token);

	RespMessage webSignUp(SignUpVo vo);

	RespMessage preSignUp(UserWxIOSVo vo);

	RespMessage resetPassword(ResetPasswordVo vo, String token);

	RespMessage realnameCert(RealNameCertVo vo, String token);

	RespMessage addBankDetail(UserDetail vo, String token);


	RespMessage updateUserWx(UserWxVo userWxVo, String token);

	RespMessage updateBank(UserDetailVo userDetailVo, String token);


    RespMessage selectRealnameCert(String token);

    RespMessage queryByUserId(String userId);

    RespMessage selectByUserId(String userId);

    RespMessage deleteMessage(String id, String token);

    RespMessage queryUserDetail(String userId, String token);

    RespMessage updateUser(User vo, String token);
}
