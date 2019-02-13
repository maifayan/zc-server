package com.yale.zc.core.util;

/**
 * 返回消息统一定义
 * @author GrayFox
 *
 */
public class RespMessage {

	// 操作状态码 
	// 0：成功 
	// 非0：失败
	public static final int SUCCESS = 0;	// 成功
	public static final int FAIL = 0x0001;	// 失败
	public static final int FAIL_LOGIN_TIMEOUT = 0x0002;	// 失败-登录超时
	public static final int FAIL_INVALID_OPEN_ID = 0x0004;	// 失败-无效的openid
	public static final int FAIL_INVALID_CATEGORY = 0x0008;	// 失败-无效的分类名
	public static final int FAIL_CREATE_AMAP_RECORD = 0x0010;	// 失败-地图api存储失败
	public static final int FAIL_SAVE_DB = 0x0020;	// 失败-数据库写入失败
	public static final int FAIL_UPDATE_AMAP = 0x0040;	// 失败-地图更新失败
	public static final int FAIL_USER_UNAUTHED = 0x0080;	// 失败-用户未授权
	public static final int FAIL_BAD_WORDS = 0x0100;		// 失败-包含敏感词
	public static final int FAIL_NEED_REQUIRED_FIELDS = 0x0200; // 失败-缺少必填字段
	public static final int FAIL_DUPILCATE_RECORDS = 0x0400; // 失败-缺少必填字段
	
	/** 操作状态码 **/
	private int errcode = SUCCESS;
	/** 返回的消息 **/
	private String errmsg = "ok";
	/** 返回的数据 **/
	private Object data;
		
	public RespMessage() {
		super();
	}

	public RespMessage(int errcode, String errmsg) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
	}
	
	public RespMessage(int errcode, Object data) {
		super();
		this.errcode = errcode;
		this.data = data;
	}

	public RespMessage(int errcode, String errmsg, Object data) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.data = data;
	}
	
	/**
	 * 默认操作成功
	 * @return RespMessage
	 */
	public static RespMessage success() {
		return new RespMessage();
	}
	
	/**
	 * 带message的操作成功
	 * @param errmsg
	 * @return RespMessage
	 */
	public static RespMessage success(String errmsg) {
		return new RespMessage(SUCCESS, errmsg);
	}
	
	/**
	 * 带data的操作成功
	 * @param data
	 * @return RespMessage
	 */
	public static RespMessage success(Object data) {
		return new RespMessage(SUCCESS, "ok", data);
	}
	
	/**
	 * 带message 和 data的操作成功
	 * @param errmsg
	 * @param data
	 * @return RespMessage
	 */
	public static RespMessage success(String errmsg, Object data) {
		return new RespMessage(SUCCESS, errmsg, data);
	}
	
	/**
	 * 默认操作失败
	 * @return RespMessage
	 */
	public static RespMessage fail() {
		return new RespMessage(FAIL, "fail");
	}
	
	/**
	 * 带errmsg的操作失败
	 * @param errmsg
	 * @return RespMessage
	 */
	public static RespMessage fail(String errmsg) {
		return new RespMessage(FAIL, errmsg);
	}
	
	/**
	 * 带errcode的操作失败
	 * @param errcode
	 * @return RespMessage
	 */
	public static RespMessage fail(int errcode) {
		return new RespMessage(errcode, "fail");
	}
	
	/**
	 * 带errcode 和 errmsg的操作失败
	 * @param errcode
	 * @param errmsg
	 * @return RespMessage
	 */
	public static RespMessage fail(int errcode, String errmsg) {
		return new RespMessage(errcode, errmsg);
	}
	
	/**
	 * 带errcode 和 errmsg 和 data的操作失败
	 * @param errcode
	 * @param errmsg
	 * @param data
	 * @return
	 */
	public static RespMessage fail(int errcode, String errmsg, Object data) {
		return new RespMessage(errcode, errmsg, data);
	}
	
	/**
	 * 带data的操作失败
	 * @param data
	 * @return RespMessage
	 */
	public static RespMessage fail(Object data) {
		return new RespMessage(FAIL, "fail", data);
	}
	
	/**
	 * 带errmsg,data的操作失败
	 * @param errmsg
	 * @param data
	 * @return RespMessage
	 */
	public static RespMessage fail(String errmsg, Object data) {
		return new RespMessage(FAIL, errmsg, data);
	}
	
	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
