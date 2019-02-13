package com.yale.zc.user.vo;

/**
 * Created by chenmingfa on 2017/7/12.
 */
public class UserWxVo {

    private String nickname;

    private Integer sex;

    private String headImageUrl;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }
}
