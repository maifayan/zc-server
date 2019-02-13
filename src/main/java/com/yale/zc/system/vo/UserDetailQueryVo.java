package com.yale.zc.system.vo;

import com.yale.zc.user.bean.User;
import com.yale.zc.user.bean.UserWx;

/**
 * Created by chenmingfa on 2017/7/31.
 */
public class UserDetailQueryVo extends User {
    UserWx userWx;

    public void setUserWx(UserWx userWx) {
        this.userWx = userWx;
    }

    public UserWx getUserWx() {
        return userWx;
    }
}
