package com.yale.zc.system.vo;

import com.yale.zc.system.bean.SysRoleModule;
import com.yale.zc.system.bean.SysUser;

import java.util.List;

public class SysSignInResultVo {

	private String token;
	private SysUser sysUser;
	private List<SysRoleModule> modules;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	public List<SysRoleModule> getModules() {
		return modules;
	}
	public void setModules(List<SysRoleModule> modules) {
		this.modules = modules;
	}
	
	
}
