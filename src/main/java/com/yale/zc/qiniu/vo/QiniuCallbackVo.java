package com.yale.zc.qiniu.vo;

public class QiniuCallbackVo {
	private String key;
	private String hash;
	private String bucket;
	private Integer fsize;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public Integer getFsize() {
		return fsize;
	}
	public void setFsize(Integer fsize) {
		this.fsize = fsize;
	}
	@Override
	public String toString() {
		return "QiniuCallbackVo [key=" + key + ", hash=" + hash + ", bucket=" + bucket + ", fsize=" + fsize + "]";
	}
	
}
