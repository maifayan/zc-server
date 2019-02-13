package com.yale.zc.system.service.impl;

import com.yale.zc.core.config.RedisConfig;
import com.yale.zc.core.config.SysUserConstant;
import com.yale.zc.core.dao.JedisClient;
import com.yale.zc.core.util.JsonUtils;
import com.yale.zc.core.util.RespMessage;
import com.yale.zc.system.bean.SysRoleModule;
import com.yale.zc.system.bean.SysUser;
import com.yale.zc.system.dao.SysRoleModuleMapper;
import com.yale.zc.system.dao.SysUserMapper;
import com.yale.zc.system.service.SysUserService;
import com.yale.zc.system.vo.SysSignInResultVo;
import com.yale.zc.system.vo.SysUserListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * 后台管理系统
 * 业务实现层
 * @author cheng
 *
 */
@Service
public class SysUserServiceImpl implements SysUserService{
	
	@Autowired
	SysUserMapper sysUserMapper;
	
	@Autowired
	SysRoleModuleMapper sysRoleModuleMapper;
	
	@Autowired
	JedisClient jedisClient;

	/**
	 * 后台管理登录
	 * 业务接口
	 * @param account 登录帐号
	 * @param password 登录密码
	 * @return 返回RespMessage信息
	 */
	@Override
	public RespMessage signIn(String account, String password, String token) {
		// 判断用户的帐号是否为空
		if(StringUtils.isEmpty(account)) {
			return RespMessage.fail(400,"用户名或密码错误");
		}	
		// 按用户帐号查询是否有这个用户
		SysUser sysUser = sysUserMapper.selectByAccount(account);
		if(sysUser == null) {
			return RespMessage.fail(400,"用户名或密码错误");
		}		
		// 判断前端传递过来的密码和数据库中查询到的密码是否一致
		// System.out.println(DigestUtils.md5DigestAsHex(password.getBytes()));
		if(! DigestUtils.md5DigestAsHex(password.getBytes()).equals(sysUser.getPassword())) {
			return RespMessage.fail(400,"用户名或密码错误");
		}	
		// 清空用户密码
		sysUser.setPassword(null);	
		// 对token进行判断，并返回 SysSignInResultVo对象
		SysSignInResultVo result = checkToken(token,sysUser);		
//		// 生成token
//		String token = UUID.randomUUID().toString();
//
//		// 把用户信息写入redis
//		jedisClient.set(RedisConfig.REDIS_SYS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(sysUser));
//		// 设置session的过期时间
//		jedisClient.expire(RedisConfig.REDIS_SYS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);		
//		SysSignInResultVo result = new SysSignInResultVo();		
//		result.setToken(token);
//		result.setSysUser(sysUser);		
		// 返回用户权限信息
		List<SysRoleModule> modules = sysRoleModuleMapper.selectByRoleId(sysUser.getSysRoleId());
		result.setModules(modules);
		// 返回token和用户信息
		return RespMessage.success(result);

	}
	
	/**
	 * 根据token 和用户的具体信息，查询并判断缓存中是否已经有token的存在
	 * @param token
	 * @param sysUser
	 * @return
	 */
	private SysSignInResultVo checkToken(String token, SysUser sysUser) {
		// 查询token在系统中是否存在
		String json = jedisClient.get(RedisConfig.REDIS_SYS_USER_SESSION_KEY + ":" + token);
		SysSignInResultVo vo = new SysSignInResultVo();
		vo.setSysUser(sysUser);
		if(json == null ) { 
			//token不存在的情况下
			// 生成token
			String newToken = UUID.randomUUID().toString();
			// 把用户信息写入redis
			jedisClient.set(RedisConfig.REDIS_SYS_USER_SESSION_KEY + ":" + newToken, JsonUtils.objectToJson(sysUser));
			// 设置session的过期时间
			jedisClient.expire(RedisConfig.REDIS_SYS_USER_SESSION_KEY + ":" + newToken, RedisConfig.REDIS_SESSION_EXPIRE);
			vo.setToken(newToken);
		} else {
			// 如果这个token在系统中已经存在，那么重新设置这个token的过期时间
			jedisClient.expire(RedisConfig.REDIS_SYS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);
			vo.setToken(token);
		}	
		return vo;
	}


	/**
	 * 通过前端的token，查询客户的信息，并返回客户的基本信息
	 * @param token
	 * @return
	 */
	@Override
	public SysUser getSysUserByToken(String token) {
		// 根据token从redis中查询用户信息
		String json = jedisClient.get(RedisConfig.REDIS_SYS_USER_SESSION_KEY + ":" + token);
		// 判断是否为空
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		// 更新过期时间
		jedisClient.expire(RedisConfig.REDIS_SYS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);
		// 返回用户信息
		return JsonUtils.jsonToPojo(json, SysUser.class);		
	}
	
	/**
	 * 退出登录
	 * @param token 前端拿到的客户token
	 * @return
	 */
	@Override
	public RespMessage signOut(String token) {
//		//根据token从redis中查询用户信息
//		String json = jedisClient.get(RedisConfig.REDIS_SYS_USER_SESSION_KEY + ":" + token);
//		if(StringUtils.isEmpty(json)){
//			return RespMessage.fail(400,"已经退出登录");
//		}
		jedisClient.del(RedisConfig.REDIS_SYS_USER_SESSION_KEY + ":" + token);
		return RespMessage.success();
	}
	/**
	 * 添加用户
	 * @param sysUser
	 * 			需要添加的用户信息
	 * @return
	 */
	@Override
	public RespMessage add(SysUser sysUser) {
//		System.out.println(sysUser.toString());
		// 判断传递的客户信息中帐号，密码这两个重要信息是否存在
		if(StringUtils.isEmpty(sysUser.getAccount())) {
			return RespMessage.fail(400,"没有账户信息，添加失败");
		}
		if(StringUtils.isEmpty(sysUser.getPassword())) {
			return RespMessage.fail(400,"密码不能为空，添加失败");
		}
		// 确认用户的账户唯一性
		String account = sysUser.getAccount();
		// 查询数据库中是否有相同的帐号
		SysUser confirmUser = sysUserMapper.selectByAccount(account);
		if(confirmUser != null){
			return RespMessage.fail(400,"帐号信息已存在，添加失败");
		}
		// 加密用户的密码
		String tempPassword = sysUser.getPassword();
		// 清空原来的密码
		sysUser.setPassword(null);
		// 设置新的密码
		sysUser.setPassword(DigestUtils.md5DigestAsHex(tempPassword.getBytes()));
		// 生成UUID
		String userId = UUID.randomUUID().toString();
		if(StringUtils.isEmpty(sysUser.getStatus())){
			sysUser.setStatus(SysUserConstant.USER_STATE_VALID);
		}
		// 设置用户主键
		sysUser.setId(userId);
	
		sysUserMapper.insertSelective(sysUser);
		return RespMessage.success();
	}

	/**
	 * 修改用户信息
	 * @param sysUser
	 * 			需要修改的用户信息
	 * @return
	 */
	@Override
	public RespMessage update(SysUser sysUser) {
		// 判断系统中是否存在这个用户
		SysUser tempUser = sysUserMapper.selectByPrimaryKey(sysUser.getId());
		if(tempUser == null){
			return RespMessage.fail(400,"用户信息不存在，无法修改");
		}
		// 如果用户存在，判断用户的状态，如果状态为失效，则不能修改用户信息
		if(tempUser.getStatus() == SysUserConstant.USER_STATE_INVALID) {
			return RespMessage.fail(400,"该用户信息已经失效，请确认用户状态后才能修改");
		}
		// 查询用户需要修改的账户是否已经在系统中存在
		if(sysUser.getAccount() != null) {
			SysUser existUser = sysUserMapper.checkExistUserAccountByUserId(new SysUser(sysUser.getId(),sysUser.getAccount()));
			if(existUser != null) {
				return RespMessage.fail(400,"用户帐号已经存在，无法修改，请重新确认帐号");
			}
		}
		// 判断用户密码是否修改，如果修改，需要清空传入的密码，进行md5加密，然后重新设置到新的用户信息中
		if(sysUser.getPassword() !=null && ! (sysUser.getPassword().equals(tempUser.getPassword()))) {
			String tempPassword = sysUser.getPassword();
			sysUser.setPassword(null);
			sysUser.setPassword(DigestUtils.md5DigestAsHex(tempPassword.getBytes()));
		}

		// 设置时间
		sysUser.setCreatedAt(null);
		sysUser.setUpdatedAt(null);
		
		// 更新用户信息
		sysUserMapper.updateByPrimaryKeySelective(sysUser);
		return RespMessage.success();
	}

	/**
	 * 物理删除用户，将用户状态修改为禁用
	 * @param userId
	 * 			用户主键id
	 * @return
	 */
	@Override
	public RespMessage delete(String userId) {
//		System.out.println(userId);
		//确认需要删除的用户信息是否传递到后台
		if(userId == null) {
			return RespMessage.fail(400,"没有接收到需要修改的用户信息，操作失败");
		}
		//判断系统中是否存在这个用户
		SysUser tempUser = sysUserMapper.selectByPrimaryKey(userId);
		if(tempUser == null) {
			return RespMessage.fail(400,"系统中没有该用户的信息，操作失败");
		}
		//判断该用户是否已经做了物理删除
		if(tempUser.getStatus() == SysUserConstant.USER_STATE_INVALID){
			return RespMessage.fail(400,"该用户已禁用，无需再次操作");
		}
		// 设置用的状态为禁用，更新修改的时间
		SysUser deleteUser = new SysUser(userId,SysUserConstant.USER_STATE_INVALID);
		// 物理删除用户
		sysUserMapper.updateAccountInvalidByPrimeryKey(deleteUser);
		return RespMessage.success();
	}
	/**
	 * 查询用户，显示全部的用户list
	 * @return
	 */
	@Override
	public RespMessage findAll() {
		// 查询所有用户，并按创建时间，用户状态排序列出		
		SysUserListVo userListVo = new SysUserListVo();
		userListVo.setSysUserList(sysUserMapper.selectAllUserOrderByStatus());
		return RespMessage.success(userListVo);
	}
	/**
	 * 按条件查询用户信息
	 * @param sysUser 
	 *          查询用户的条件
	 * @return
	 */
	@Override
	public RespMessage select(SysUser sysUser) {
		SysUserListVo userListVo = new SysUserListVo();
		// 表示没有找到符合条件的SysUser集合
		if(sysUserMapper.selectSelective(sysUser) == null){
			return RespMessage.fail(400,"没有符合要求的记录，查询失败");
		}
		// 找到符合要求的记录，并返回
		userListVo.setSysUserList(sysUserMapper.selectSelective(sysUser));
		return RespMessage.success(userListVo);	
	}
	
	
}
