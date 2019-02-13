package com.yale.zc.system.bean;

import java.util.Date;

public class SysRoleModule {
    private String roleId;

    private String moduleId;

    private Date createdAt;

    private Date updatedAt;

	public SysRoleModule() {}

    public SysRoleModule(String roleId, String moduleId) {
		this.roleId = roleId;
		this.moduleId = moduleId;
	}

	public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId == null ? null : moduleId.trim();
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
}