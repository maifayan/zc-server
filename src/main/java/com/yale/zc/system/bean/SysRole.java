package com.yale.zc.system.bean;

import java.util.Date;

public class SysRole {
    private String id;

    private String name;

    private Integer status;

    private Date createdAt;

    private Date updatedAt;
    
    public SysRole() {
		super();
	}
 
	public SysRole(String id, Integer status, Date updatedAt) {
		super();
		this.id = id;
		this.status = status;
		this.updatedAt = updatedAt;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

  
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "SysRole [id=" + id + ", name=" + name + ", status=" + status + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
    
    
}