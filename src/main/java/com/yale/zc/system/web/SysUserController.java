package com.yale.zc.system.web;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.system.bean.SysUser;
import com.yale.zc.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理模块 视图层
 * 
 * @author SherryCheng
 *
 */
@RestController
@RequestMapping("/sys")
public class SysUserController {

	@Autowired
	SysUserService sysUserService;

	/**
	 * 后台管理登录
	 * 
	 * @param account
	 *            登录帐号
	 * @param password
	 *            登录密码
	 * @return 返回RespMessage信息
	 */
	@RequestMapping("/sign-in")
	public RespMessage signIn(String account, String password, String token) {
		return sysUserService.signIn(account, password, token);
	}
	
	/**
	 * 退出登录
	 * 
	 * @param token
	 *            前端拿到的客户token
	 * @return
	 */
	@RequestMapping("/sign-out")
	public RespMessage signOut(String token) {
		return sysUserService.signOut(token);
	}
	
	/**
	 * 通过前端的token，查询客户的信息，并返回客户的基本信息
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping("/token")
	public SysUser getSysUserByToken(String token) {
		return sysUserService.getSysUserByToken(token);
	}


	/**
	 *添加管理用户
	 * @param sysUser
	 * 			需要添加的用户信息
	 * @return
	 */
	@RequestMapping("/add")
	public RespMessage add(SysUser sysUser) {
		return sysUserService.add(sysUser);
	}
	/**
	 * 修改用户信息
	 * @param sysUser
	 * 			需要修改的用户信息
	 * @return
	 */
	@RequestMapping("/update")
	public RespMessage update(SysUser sysUser){		
		return sysUserService.update(sysUser);	
	}
	/**
	 * 物理删除用户，将用户状态修改为禁用
	 * @param userId
	 * 			用户主键id
	 * @return
	 */
	@RequestMapping("/delete")
	public RespMessage delete(String userId){
		return sysUserService.delete(userId);
	}
	/**
	 * 查询用户，显示全部的用户list
	 * @return
	 */
	@RequestMapping("/findAll")
	public RespMessage findAll(){
		return sysUserService.findAll();
	}
	/**
	 * 按条件查询用户信息
	 * @param sysUser 
	 *          查询用户的条件
	 * @return
	 */
	@RequestMapping("/select")
	public RespMessage select(SysUser sysUser){
		return sysUserService.select(sysUser);	
	}
}
