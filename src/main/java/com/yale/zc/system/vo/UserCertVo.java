package com.yale.zc.system.vo;

public class UserCertVo {
	
    private String userId;		// 用户id

    private Integer method;		// 审核方式 0-人工 1-其他

    private Integer result;		// 认证结果 0-成功 1-驳回

    private String comment;		// 备注

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
