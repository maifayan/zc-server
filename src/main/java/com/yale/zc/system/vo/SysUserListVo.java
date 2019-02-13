package com.yale.zc.system.vo;

import com.yale.zc.system.bean.SysUser;

import java.util.ArrayList;
import java.util.List;

public class SysUserListVo {
	
	private List<SysUser>sysUserList = new ArrayList<SysUser>();

	public List<SysUser> getSysUserList() {
		return sysUserList;
	}

	public void setSysUserList(List<SysUser> sysUserList) {
		this.sysUserList = sysUserList;
	}
}
