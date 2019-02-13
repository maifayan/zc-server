package com.yale.zc.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
@ConfigurationProperties(prefix = "weixin")
public class WeixinConfig {
	
	@Value(value="${weixin.appid}")
	private String appid;
	
	@Value(value="${weixin.appsecret}")
	private String appsecret;
	
	@Value(value="${weixin.oauth.auth-url}")
	private String authUrl;
	
	@Value(value="${weixin.oauth.scope}")
	private String scope;
	
	@Value(value="${weixin.oauth.callback-url}")
	private String callbackUrl;

	@Value(value="${weixin.oauth.token-url}")
	private String tokenUrl;
	
	@Value(value="${weixin.oauth.info-url}")
	private String infoUrl;
	
	@Value(value="${weixin.oauth.oauth-redirect-url}")
	public String oauthRedirectUrl;

	public String getAuthUrl() throws UnsupportedEncodingException {
		String url = authUrl.replace("APPID", appid)
				.replace("REDIRECT_URI", URLEncoder.encode(callbackUrl, "utf-8"))
				.replace("SCOPE", scope);
		return url;
	}
	
	public String getTokenUrl(String code) {
		String url = tokenUrl.replace("APPID", appid)
				.replace("SECRET", appsecret)
				.replace("CODE", code);
		return url;
	}
	
	public String getInfoUrl(String accessToken, String openId) {
		String url = infoUrl.replace("ACCESS_TOKEN", accessToken)
				.replace("OPENID", openId);
		return url;
	}
}
