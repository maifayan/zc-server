package com.yale.zc.system.service;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.system.bean.SysRole;

import java.util.List;

/**
 * 后台管理
 * 角色模块
 * 业务层
 * @author cheng
 *
 */
public interface SysRoleService {
	/**
	 * 添加角色
	 * @param role
	 * 			需要添加的角色信息
	 * @return
	 */
	RespMessage add(SysRole role);
	/**
	 * 设置角色的权限
	 * @param roleId
	 * 			需要添加权限的角色名称
	 * @param SysModuleId
	 * 			需要添加的权限集合
	 * @return
	 */
	RespMessage setRoleModule(String roleId, List<String> sysModuleId);
	/**
	 * 查询所有的角色信息
	 * @return
	 */
	RespMessage findAll();
	/**
	 * 根据条件，查询角色
	 * @param role
	 * 			需要查询的角色的信息
	 * @return
	 */
	RespMessage select(SysRole role);
	/**
	 * 修改角色信息
	 * @param role
	 * 			需要修改的角色信息
	 * @return
	 */
	RespMessage update(SysRole role);
	/**
	 * 删除角色
	 * @param roleId
	 * 			需要删除的角色id
	 * @return
	 */
	RespMessage delete(String roleId);
	/**
	 * 修改角色的状态
	 * @param roleId
	 * 			需要修改状态的角色id
	 * @return
	 */
	RespMessage changeRoleStatue(String roleId);
	/**
	 * 更新角色权限
	 * @param roleId
	 * 			角色id
	 * @param SysModuleId
	 * 			更新后的权限集合
	 * @return
	 */
	RespMessage updateRoleModule(String roleId, List<String> sysModuleId);

	
	

	
}
