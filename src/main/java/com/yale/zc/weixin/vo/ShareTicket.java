package com.yale.zc.weixin.vo;

/**
 * /**
 * AccessToken的数据模型
 */
public class ShareTicket {
    //获取到的凭证
    private String ticket;
    //凭证有效时间，单位：秒
    private int expires_in;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
