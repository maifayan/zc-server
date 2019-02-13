package com.yale.zc.system.service.impl;

import com.yale.zc.core.config.SysRoleConstant;
import com.yale.zc.core.util.RespMessage;
import com.yale.zc.system.bean.SysRole;
import com.yale.zc.system.bean.SysRoleModule;
import com.yale.zc.system.bean.SysUser;
import com.yale.zc.system.dao.SysRoleMapper;
import com.yale.zc.system.dao.SysRoleModuleMapper;
import com.yale.zc.system.dao.SysUserMapper;
import com.yale.zc.system.service.SysRoleService;
import com.yale.zc.system.vo.SysRoleListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SysRoleServiceImpl implements SysRoleService{
	
	@Autowired
	SysRoleMapper sysRoleMapper;
	
	@Autowired
	SysRoleModuleMapper sysRoleModuleMapper;
	
	@Autowired
	SysUserMapper sysUserMapper;

	@Override
	public RespMessage add(SysRole role) {
		// 校验传递过来的角色信息
		if(role.getName() == null) {
			return RespMessage.fail(400,"信息不完整，无法添加角色");
		}
		// 角色唯一性校验
		SysRole tempRole = sysRoleMapper.selectByRoleName(role.getName());
		if(tempRole != null) {
			return RespMessage.fail(400,"该角色已经存在，添加失败");
		}
		// 生成主键的uuid
		String roleId = UUID.randomUUID().toString();
		role.setId(roleId);
		// 添加角色
		sysRoleMapper.insertSelective(role);
		return RespMessage.success();
	}

	@Override
	public RespMessage setRoleModule(String roleId, List<String> sysModuleId) {
		// 基础信息校验
		if(roleId == null) {
			return RespMessage.fail(400,"请确认需要添加权限的角色");
		}
		if(sysModuleId.isEmpty()) {
			return RespMessage.fail(400,"没有需要添加的权限信息，权限添加失败");
		}
		// 状态及安全性校验
		SysRole tempRole = sysRoleMapper.selectByPrimaryKey(roleId);
		if(tempRole == null) {
			return RespMessage.fail(400,"没有该角色信息，无法添加权限");
		}
		if(tempRole.getStatus() == SysRoleConstant.ROLE_STATUS_VALID) {
			return RespMessage.fail(400,"该角色已经禁用，无法添加权限");
		}
		// 添加角色的权限
		for(int i =0;i<sysModuleId.size();i++) {
			sysRoleModuleMapper.insertSelective(new SysRoleModule(roleId,sysModuleId.get(i)));
		}
		return RespMessage.success();
	}

	@Override
	public RespMessage findAll() {
		SysRoleListVo roleVo = new SysRoleListVo();
		roleVo.setSysRoleList(sysRoleMapper.selectAll());
		return RespMessage.success(roleVo);
	}

	@Override
	public RespMessage select(SysRole role) {
		// 判断条件查询的结果
		if(sysRoleMapper.selectSelective(role).isEmpty()) {
			return RespMessage.fail(400,"没有符合条件的角色");
		}
		SysRoleListVo roleVo = new SysRoleListVo();
		roleVo.setSysRoleList(sysRoleMapper.selectSelective(role));
		return RespMessage.success(roleVo);
	}

	@Override
	public RespMessage update(SysRole role) {	
		// 校验用户是否存在以及目前的状态
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(role.getId());
		if(sysRole == null) {
			return RespMessage.fail(400,"角色不存在，修改失败");
		}
		if(sysRole.getStatus() == SysRoleConstant.ROLE_STATUS_INVALID) {
			return RespMessage.fail(400,"角色已禁用，修改失败");
		}
		// 确认该角色目前是否有相关的人员用户
		List<SysUser> userList = sysUserMapper.selectSelective(new SysUser(role.getId()));
		if(!(userList.isEmpty())) {
			return RespMessage.fail(400,"有用户正在使用该角色，无法修改");
		}
		// 如果角色名称非空，确认新的角色名称是否为唯一
		if(role.getName() != null) {
			SysRole confirmRole = sysRoleMapper.confirmRoleName(role);
			if(confirmRole != null) {
				return RespMessage.fail(400,"该角色名称已存在，无法修改");
			}
		} 
		// 修改角色
		sysRoleMapper.updateByPrimaryKeySelective(role);
		return RespMessage.success();
	}

	@Override
	public RespMessage delete(String roleId) {
		// 根据角色ID确认角色是否存在及其状态
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
		if(sysRole == null) {
			return RespMessage.fail(400,"角色不存在，删除失败");
		}
		if(sysRole.getStatus() == SysRoleConstant.ROLE_STATUS_INVALID) {
			return RespMessage.fail(400,"角色已禁用，删除失败");
		}
		// 确认该角色下是否有用户正在使用
		List<SysUser> userList = new ArrayList<SysUser>();
		userList = sysUserMapper.selectSelective(new SysUser(roleId));
		if(!(userList.isEmpty())) {
			return RespMessage.fail(400,"有用户正在使用该角色，无法删除");
		}
		//物理删除角色
		sysRoleMapper.updateRoleMapper(new SysRole(roleId,SysRoleConstant.ROLE_STATUS_INVALID,new Date()));
		return RespMessage.success();
	}

	@Override
	public RespMessage changeRoleStatue(String roleId) {
		// 确认该角色下是否有用户正在使用
		List<SysUser> userList = sysUserMapper.selectSelective(new SysUser(roleId));
		if(!(userList.isEmpty())) {
			return RespMessage.fail(400,"有用户正在使用该角色，无法修改状态");
		}
		//跟均角色id，查询原来的角色信息
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
		int roleStatue = 
				((sysRole.getStatus() == SysRoleConstant.ROLE_STATUS_VALID) ? SysRoleConstant.ROLE_STATUS_INVALID: SysRoleConstant.ROLE_STATUS_VALID);
		// 修改用户的状态
		sysRoleMapper.updateRoleMapper(new SysRole(roleId,roleStatue,new Date()));
		return RespMessage.success();
	}

	@Override
	public RespMessage updateRoleModule(String roleId,List<String> sysModuleId) {
		// 根据角色ID确认角色是否存在及其状态
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
		if(sysRole == null) {
			return RespMessage.fail(400,"角色不存在，无法修改权限");
		}
		if(sysRole.getStatus() == SysRoleConstant.ROLE_STATUS_INVALID) {
			return RespMessage.fail(400,"角色已禁用，无法修改权限");
		}
		if(sysModuleId.isEmpty()) {
			return RespMessage.fail(400,"没有找到角色的权限信息，无法修改");
		}
		//清楚原有的权限记录
		sysRoleModuleMapper.deleteByRoleId(roleId);
		//增加新的权限记录
		for(int i = 0; i < sysModuleId.size(); i++) {
			sysRoleModuleMapper.insertSelective(new SysRoleModule(roleId,sysModuleId.get(i)));	
		}
		return RespMessage.success();
	}

	
}
