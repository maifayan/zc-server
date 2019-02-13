package com.yale.zc.system.web;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.system.bean.SysRole;
import com.yale.zc.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台管理
 * 角色模块
 * 视图层
 * @author cheng
 *
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {
	
	@Autowired
	SysRoleService sysRoleService;
	/**
	 * 添加角色
	 * @param role
	 * 			需要添加的角色信息
	 * @return
	 */
	@RequestMapping("/add")
	public RespMessage add(SysRole role) {
//		System.out.println("controller arrive");
		return sysRoleService.add(role);
	}
	/**
	 * 设置角色的权限
	 * @param roleId
	 * 			需要添加权限的角色名称
	 * @param SysModuleId
	 * 			需要添加的权限集合
	 * @return
	 */
	@RequestMapping("/set-module")
	public RespMessage setRoleModule(String roleId,List<String>SysModuleId) {
		return sysRoleService.setRoleModule(roleId,SysModuleId);
	}
	/**
	 * 查询所有的角色信息
	 * @return
	 */
	@RequestMapping("/findAll")
	public RespMessage findAll() {
		return sysRoleService.findAll();
	}
	/**
	 * 根据条件，查询角色
	 * @param role
	 * 			需要查询的角色的信息
	 * @return
	 */
	@RequestMapping("/select")
	public RespMessage select(SysRole role) {
		return sysRoleService.select(role);
	}
	/**
	 * 修改角色信息
	 * @param role
	 * 			需要修改的角色信息
	 * @return
	 */
	@RequestMapping("/update")
	public RespMessage update(SysRole role) {
		return sysRoleService.update(role);
	}

	/**
	 * 删除角色
	 * @param roleId
	 * 			需要删除的角色id
	 * @return
	 */
	@RequestMapping("/delete")
	public RespMessage delete(String roleId) {
		return sysRoleService.delete(roleId);
	}
	/**
	 * 修改角色的状态
	 * @param roleId
	 * 			需要修改状态的角色id
	 * @return
	 */
	@RequestMapping("/changeRoleStatue")
	public RespMessage changeRoleStatue(String roleId) {	
		return sysRoleService.changeRoleStatue(roleId);
	}
	/**
	 * 更新角色权限
	 * @param roleId
	 * 			角色id
	 * @param SysModuleId
	 * 			更新后的权限集合
	 * @return
	 */
	@RequestMapping("/update-module")
	public RespMessage updateRoleModule(String roleId,List<String>SysModuleId) {
		return sysRoleService.updateRoleModule(roleId,SysModuleId);
	}
}
