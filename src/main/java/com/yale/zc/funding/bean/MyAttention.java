package com.yale.zc.funding.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author maifayan
 * @email
 * @date 2017-12-28 9:56:13
 */
public class MyAttention implements Serializable {

    //attention_id
    private String id;
    //uuid
    private String fundingId;
    //用户id
    private String userId;
    //关注状态
    private Integer status;
    //
    private Date createdAt;
    //
    private Date updatedAt;
    /**
     * 获取：attentionId
     */
    public String getId() {
        return id;
    }
    /**
     * 设置：attentionId
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 获取：fundingId
     */
    public String getFundingId() {
        return fundingId;
    }
    /**
     * 设置：fundingId
     */
    public void setFundingId(String fundingId) {
        this.fundingId = fundingId;
    }
    /**
     * 获取：status
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * 设置：status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    //
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
   //
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
