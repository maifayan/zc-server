package com.yale.zc.funding.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author victorZhang
 * @email 
 * @date 2018-01-02 14:15:40
 */
public class FundingLink implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private String id;
	//众筹项目id
	private String fundingId;
	//链接地址
	private String linkUrl;
	//0-视频链接  1-海报 2-跑马灯图片
	private Integer type;
	//链接描述
	private String desc;
	//
	private Date createdAt;
	//
	private Date updatedAt;

	/**
	 * 设置：图片id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：图片id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：众筹项目id
	 */
	public void setFundingId(String fundingId) {
		this.fundingId = fundingId;
	}
	/**
	 * 获取：众筹项目id
	 */
	public String getFundingId() {
		return fundingId;
	}
	/**
	 * 设置：链接地址
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	/**
	 * 获取：链接地址
	 */
	public String getLinkUrl() {
		return linkUrl;
	}
	/**
	 * 设置：0-视频链接  1-海报 2-跑马灯图片
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：0-视频链接  1-海报 2-跑马灯图片
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：链接描述
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * 获取：链接描述
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * 设置：
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * 获取：
	 */
	public Date getCreatedAt() {
		return createdAt;
	}
	/**
	 * 设置：
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	/**
	 * 获取：
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}
}
