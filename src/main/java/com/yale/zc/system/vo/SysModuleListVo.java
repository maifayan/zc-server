package com.yale.zc.system.vo;

import com.yale.zc.system.bean.SysModule;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理
 * 权限集合
 * @author cheng
 *
 */
public class SysModuleListVo {
	
	private List<SysModule>sysModule = new ArrayList<SysModule>();

	public List<SysModule> getSysModule() {
		return sysModule;
	}

	public void setSysModule(List<SysModule> sysModule) {
		this.sysModule = sysModule;
	}
}
