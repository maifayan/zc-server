package com.yale.zc.system.vo;

import com.yale.zc.system.bean.SysRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色查询，返回所有角色的list集合的实体
 * @author cheng
 *
 */
public class SysRoleListVo {
	
	private List<SysRole>sysRoleList = new ArrayList<SysRole>();

	public List<SysRole> getSysRoleList() {
		return sysRoleList;
	}

	public void setSysRoleList(List<SysRole> sysRoleList) {
		this.sysRoleList = sysRoleList;
	}
}
