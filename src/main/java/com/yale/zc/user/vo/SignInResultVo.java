package com.yale.zc.user.vo;

public class SignInResultVo {

	private String token;
	private UserRedisVo userInfoWrapper;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserRedisVo getUserInfoWrapper() {
		return userInfoWrapper;
	}
	public void setUserInfoWrapper(UserRedisVo userInfoWrapper) {
		this.userInfoWrapper = userInfoWrapper;
	}
	
}
