package com.yale.zc.qrcode.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by asus on 2017/12/2.
 */

@ConfigurationProperties("qrcode")
public class QrcodeSettings {
    private String qrcodeurl;


    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl;
    }


}
