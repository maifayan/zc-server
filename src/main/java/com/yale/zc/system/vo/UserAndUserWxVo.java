package com.yale.zc.system.vo;


import com.yale.zc.user.bean.User;
import com.yale.zc.user.bean.UserWx;

/**
 * Created by asus on 2017/9/20.
 */
public class UserAndUserWxVo {
   User user;
   UserWx userWx;

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserWx(UserWx userWx) {
        this.userWx = userWx;
    }

    public User getUser() {
        return user;
    }

    public UserWx getUserWx() {
        return userWx;
    }
}
