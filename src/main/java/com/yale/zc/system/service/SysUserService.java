package com.yale.zc.system.service;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.system.bean.SysUser;

/**
 * 后台用户模块
 * 业务层接口
 * @author cheng
 *
 */
public interface SysUserService {
	/**
	 * 后台管理登录
	 * 业务接口
	 * @param account 登录帐号
	 * @param password 登录密码
	 * @return 返回RespMessage信息
	 */
	RespMessage signIn(String account, String password, String token);

	/**
	 * 通过前端的token，查询客户的信息，并返回客户的基本信息
	 * @param token
	 * @return
	 */
	SysUser getSysUserByToken(String token);
	/**
	 * 退出登录
	 * @param token 前端拿到的客户token
	 * @return
	 */
	RespMessage signOut(String token);
	/**
	 * 添加用户
	 * @param sysUser
	 * 			需要添加的用户信息
	 * @return
	 */
	RespMessage add(SysUser sysUser);
	/**
	 * 修改用户信息
	 * @param sysUser
	 * 			需要修改的用户信息
	 * @return
	 */
	RespMessage update(SysUser sysUser);
	/**
	 * 物理删除用户，将用户状态修改为禁用
	 * @param userId
	 * 			用户主键id
	 * @return
	 */
	RespMessage delete(String userId);
	/**
	 * 查询用户，显示全部的用户list
	 * @return
	 */
	RespMessage findAll();
	/**
	 * 按条件查询用户信息
	 * @param sysUser 查询用户的条件
	 * @return
	 */
	RespMessage select(SysUser sysUser);
}
