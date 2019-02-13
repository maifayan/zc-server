package com.yale.zc.system.web;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.system.service.SysUserCertLogService;
import com.yale.zc.system.vo.UserCertVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理
 * 用户实名认证模块
 * 控制层
 * @author cheng
 *
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserCertLogController {
	
	@Autowired
	SysUserCertLogService sysUserCertLogService;

	/**
	 * 列出待认证用户信息
	 * @return
	 */
	@RequestMapping("/list-submitted")
	public RespMessage listSubmitted() {
		return sysUserCertLogService.listSubmitted();
	}

	/**
	 * 批复认证请求
	 * @param vo
	 * 认证结果
	 * @param token
	 * token
	 * @return
	 */
	@RequestMapping("/audit")
	public RespMessage audit(UserCertVo vo, String token) {
		return sysUserCertLogService.audit(vo, token);
	}	
	/**
	 * 列出所有用户认证记录
	 * @return
	 */
	@RequestMapping("/list")
	public RespMessage list() {
		return sysUserCertLogService.list();
	}

	/**
	 * 列出本人批复的信息
	 * @return
	 */
	@RequestMapping("/list-my")
	public RespMessage listMy(String token) {
		return sysUserCertLogService.listMy(token);
	}
}
