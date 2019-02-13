package com.yale.zc.system.service;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.system.vo.UserCertVo;

/**
 * 后台管理
 * 用户实名认证模块
 * 业务层接口
 * @author cheng
 *
 */
public interface SysUserCertLogService {

	RespMessage listSubmitted();
	
	RespMessage audit(UserCertVo vo, String token);
	
	RespMessage list();
	
	RespMessage listMy(String token);
}
