package com.yale.zc.funding.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author victorZhang
 * @email 
 * @date 2017-12-26 15:06:13
 */
public class CrowdFunding implements Serializable {
	private static final long serialVersionUID = 1L;
	

	//uuid
	private String id;
	//活动标题
	private String title;
	//发布者id
	private String publisherId;
	//头像地址
	private String headImageUrl;

	//报名截止时间
	private Date enrolEndDate;
	//活动开始时间
	private Date startDate;
	//人数上限
	private Integer limitNum;
	//活动金额
	private Integer amount;
	//省
	private String province;
	//市
	private String city;
	//其它地区
	private String otherArea;
	//众筹宣言
	private String declaration;
	//支持宣言
	private String supportDeclaration;
	//活动详情
	private String details;
	//报名相关
	private String related;
	//活动标准
	private String standard;
	//经度
	private Double lng;
	//纬度
	private Double lat;
	//浏览计数
	private Long viewCount;
	//分享计数
	private Long shareCount;
	//收藏计数
	private Long favoriteCount;
	//是否固顶 0-否 1-是
	private Integer topFix;
	//发布状态 0-已发布 1-未发布 2-取消
	private Integer status;
	//自定义字段
	private Object customFields;
	//
	private Date createdAt;
	//
	private Date updatedAt;
	/**
	 * 设置：uuid
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：uuid
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：活动标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：活动标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：发布者id
	 */
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	/**
	 * 获取：发布者id
	 */
	public String getPublisherId() {
		return publisherId;
	}
	/**
	 * 设置：头像地址
	 */
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	/**
	 * 获取：头像地址
	 */
	public String getHeadImageUrl() {
		return headImageUrl;
	}

	/**
	 * 设置：报名截止时间
	 */
	public void setEnrolEndDate(Date enrolEndDate) {
		this.enrolEndDate = enrolEndDate;
	}
	/**
	 * 获取：报名截止时间
	 */
	public Date getEnrolEndDate() {
		return enrolEndDate;
	}
	/**
	 * 设置：活动开始时间
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * 获取：活动开始时间
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * 设置：人数上限
	 */
	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}
	/**
	 * 获取：人数上限
	 */
	public Integer getLimitNum() {
		return limitNum;
	}
	/**
	 * 设置：活动金额
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	/**
	 * 获取：活动金额
	 */
	public Integer getAmount() {
		return amount;
	}
	/**
	 * 设置：省
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：省
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：其它地区
	 */
	public void setOtherArea(String otherArea) {
		this.otherArea = otherArea;
	}
	/**
	 * 获取：其它地区
	 */
	public String getOtherArea() {
		return otherArea;
	}
	/**
	 * 设置：众筹宣言
	 */
	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}
	/**
	 * 获取：众筹宣言
	 */
	public String getDeclaration() {
		return declaration;
	}
	/**
	 * 设置：支持宣言
	 */
	public void setSupportDeclaration(String supportDeclaration) {
		this.supportDeclaration = supportDeclaration;
	}
	/**
	 * 获取：支持宣言
	 */
	public String getSupportDeclaration() {
		return supportDeclaration;
	}
	/**
	 * 设置：活动详情
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * 获取：活动详情
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * 设置：报名相关
	 */
	public void setRelated(String related) {
		this.related = related;
	}
	/**
	 * 获取：报名相关
	 */
	public String getRelated() {
		return related;
	}
	/**
	 * 设置：活动标准
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}
	/**
	 * 获取：活动标准
	 */
	public String getStandard() {
		return standard;
	}
	/**
	 * 设置：经度
	 */
	public void setLng(Double lng) {
		this.lng = lng;
	}
	/**
	 * 获取：经度
	 */
	public Double getLng() {
		return lng;
	}
	/**
	 * 设置：纬度
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}
	/**
	 * 获取：纬度
	 */
	public Double getLat() {
		return lat;
	}
	/**
	 * 设置：浏览计数
	 */
	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}
	/**
	 * 获取：浏览计数
	 */
	public Long getViewCount() {
		return viewCount;
	}
	/**
	 * 设置：分享计数
	 */
	public void setShareCount(Long shareCount) {
		this.shareCount = shareCount;
	}
	/**
	 * 获取：分享计数
	 */
	public Long getShareCount() {
		return shareCount;
	}
	/**
	 * 设置：收藏计数
	 */
	public void setFavoriteCount(Long favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	/**
	 * 获取：收藏计数
	 */
	public Long getFavoriteCount() {
		return favoriteCount;
	}
	/**
	 * 设置：是否固顶 0-否 1-是
	 */
	public void setTopFix(Integer topFix) {
		this.topFix = topFix;
	}
	/**
	 * 获取：是否固顶 0-否 1-是
	 */
	public Integer getTopFix() {
		return topFix;
	}
	/**
	 * 设置：发布状态 0-已发布 1-未发布 2-取消
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：发布状态 0-已发布 1-未发布 2-取消
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：自定义字段
	 */
 	public void setCustomFields(Object customFields) {
		this.customFields = customFields;
	}

	public Object getCustomFields() {
		return customFields;
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
