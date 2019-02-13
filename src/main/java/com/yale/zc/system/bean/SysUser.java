package com.yale.zc.system.bean;

import java.util.Date;

public class SysUser {
    private String id;

    private String account;

    private String password;

    private String dept;

    private String name;

    private Integer sex;

    private String logo;

    private String mobile;

    private String telephone;

    private String email;

    private Integer status;

    private Date createdAt;

    private Date updatedAt;

    private String sysRoleId;

    public SysUser() {}
    
	public SysUser(String sysRoleId) {
		this.sysRoleId = sysRoleId;
	}
	
	public SysUser(String id, String account) {
		super();
		this.id = id;
		this.account = account;
	}

	public SysUser(String id, Integer status) {
		super();
		this.id = id;
		this.status = status;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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

    public String getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(String sysRoleId) {
        this.sysRoleId = sysRoleId == null ? null : sysRoleId.trim();
    }

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", account=" + account + ", password=" + password + ", dept=" + dept + ", name="
				+ name + ", sex=" + sex + ", logo=" + logo + ", mobile=" + mobile + ", telephone=" + telephone
				+ ", email=" + email + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", sysRoleId=" + sysRoleId + "]";
	}
    
    
}