package com.yale.zc.user.vo;

import com.yale.zc.user.bean.User;
import com.yale.zc.user.bean.UserWx;

public class UserRedisVo {
	private User user;
	private UserWx userWx;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserWx getUserWx() {
		return userWx;
	}
	public void setUserWx(UserWx userWx) {
		this.userWx = userWx;
	}
	
}
