package com.yale.zc.qiniu.vo;

public class QiniuTokenVo {

	private String token;
	private String key;
	
	public QiniuTokenVo() {
		super();
	}
	public QiniuTokenVo(String token, String key) {
		super();
		this.token = token;
		this.key = key;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "QiniuTokenVo [token=" + token + ", key=" + key + "]";
	}
	
	
}
