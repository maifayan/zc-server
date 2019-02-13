package com.yale.zc.qrcode.config;


public class MatrixToBgImageConfig {
	public MatrixToBgImageConfig(int qrcode_height, int qrcode_x, int qrcode_y) {
		this();
		this.qrcode_height = qrcode_height;
		this.qrcode_x = qrcode_x;
		this.qrcode_y = qrcode_y;
	}

	public MatrixToBgImageConfig() {
	}

	// logo默认边框颜色
	public static final String DEFAULT_HEADIMGURL = "default_headimg.jpg";
	private String bgFileName = "bg.png";// 背景图片



	private String logoFileName="fgx-logo.png";
	//二维码内置跳转URL
	private String qrCodeUrl;

	//生成的临时文件路径
	private String tempPath;
	//微信头像
	private String headimgUrl;// 头像
	private int headimg_height = 204;// 头像的高度
	private int headimg_x = 196;// 头像的X
	private int headimg_y = 104;// 头像的y

	//微信昵称
	private String realname;// 微信昵称
	private int realname_x = 240;// 微信昵称的x
	private int realname_y = 350;// 微信昵称的y

	/*//二维码介绍

	private String qrcodeDesc1  ;// 二维码介绍语句
	private int qrcodeDesc1_x = 254;// 二维码介绍语句的x
	private int qrcodeDesc1_y = 176;// 二维码介绍语句的y
*/
	//二维码
	private int qrcode_height = 160;// 二维码的高度
	private int qrcode_x = 310;// 二维码的X
	private int qrcode_y = 716;// 二维码的y

/*	//二维码logo
	private int qrcode_logo_height = 60;// 二维码的高度
	private int qrcode_logo__x = 552;// 二维码的X
	private int qrcode_logo__y = 118;// 二维码的y

	//二维码操作语句（长按二维码关注房公信）

	private String qrcodeDesc2  ;// 二维码介绍语句
	private int qrcodeDesc2_x = 490;// 二维码介绍语句的x
	private int qrcodeDesc2_y = 294;// 二维码介绍语句的y*/
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}


	public int getQrcode_height() {
		return qrcode_height;
	}

	public void setQrcode_height(int qrcode_height) {
		this.qrcode_height = qrcode_height;
	}

	public int getQrcode_x() {
		return qrcode_x;
	}

	public void setQrcode_x(int qrcode_x) {
		this.qrcode_x = qrcode_x;
	}

	public int getQrcode_y() {
		return qrcode_y;
	}

	public void setQrcode_y(int qrcode_y) {
		this.qrcode_y = qrcode_y;
	}

	public String getBgFileName() {
		return bgFileName;
	}

	public void setBgFileName(String bgFileName) {
		this.bgFileName = bgFileName;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getHeadimgUrl() {
		return headimgUrl;
	}

	public void setHeadimgUrl(String headimgUrl) {
		this.headimgUrl = headimgUrl;
	}

	public int getHeadimg_height() {
		return headimg_height;
	}

	public void setHeadimg_height(int headimg_height) {
		this.headimg_height = headimg_height;
	}

	public int getHeadimg_x() {
		return headimg_x;
	}

	public void setHeadimg_x(int headimg_x) {
		this.headimg_x = headimg_x;
	}

	public int getHeadimg_y() {
		return headimg_y;
	}

	public void setHeadimg_y(int headimg_y) {
		this.headimg_y = headimg_y;
	}

	public int getRealname_x() {
		return realname_x;
	}

	public void setRealname_x(int realname_x) {
		this.realname_x = realname_x;
	}

	public int getRealname_y() {
		return realname_y;
	}

	public void setRealname_y(int realname_y) {
		this.realname_y = realname_y;
	}

}
