package com.yale.zc.system.service.impl;

import com.yale.zc.core.config.Constant;
import com.yale.zc.core.util.DateUtil;
import com.yale.zc.core.util.MessageToUserUtil;
import com.yale.zc.core.util.RespMessage;
import com.yale.zc.system.bean.SysMessageToUser;
import com.yale.zc.system.bean.SysUser;
import com.yale.zc.system.bean.UserCertLog;
import com.yale.zc.system.dao.SysMessageToUserMapper;
import com.yale.zc.system.dao.UserCertLogMapper;
import com.yale.zc.system.service.SysUserCertLogService;
import com.yale.zc.system.service.SysUserService;
import com.yale.zc.system.vo.UserCertVo;
import com.yale.zc.system.vo.UserDetailQueryVo;
import com.yale.zc.user.bean.User;
import com.yale.zc.user.bean.UserWx;
import com.yale.zc.user.dao.UserDetailMapper;
import com.yale.zc.user.dao.UserMapper;
import com.yale.zc.user.dao.UserWxMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 后台管理
 * 经纪人认证模块
 * 业务层
 * @author cheng
 *
 */
@Service
public class SysUserCertLogServiceImpl implements SysUserCertLogService {

	@Autowired
	UserCertLogMapper userCertLogMapper;

	@Autowired
	UserMapper userMapper;

	@Autowired
	SysUserService sysUserService;

	@Autowired
	UserWxMapper userWxMapper;

	@Autowired
	SysMessageToUserMapper sysMessageToUserMapper;

	@Autowired
	UserDetailMapper userDetailMapper;


	@Override
	public RespMessage listSubmitted() {
		// TODO Auto-generated method stub
		List<User> users = userMapper.selectByRealnameCertStatus(Constant.AUDIT_STATUS_SUBMITTED);
		// 加上userWx
		List<UserDetailQueryVo> result = new ArrayList<>();
		for (User user : users) {
			UserWx userWx = userWxMapper.selectByUserId(user.getId());

			UserDetailQueryVo vo = new UserDetailQueryVo();
			BeanUtils.copyProperties(user, vo);
			vo.setUserWx(userWx);
			result.add(vo);
		}
		return RespMessage.success(result);
	}

	@Override
	public RespMessage audit(UserCertVo vo, String token) {

		// 对UserCertLog这张表加一个保护
		List<UserCertLog> userCertLogs = userCertLogMapper.selectByUserId(vo.getUserId());

		//System.out.println("#userCertLogs:"+userCertLogs+"#vo.getResult():"+vo.getResult());

		for (UserCertLog s : userCertLogs) {
			if(s.getResult() == Constant.AUDIT_STATUS_OK) {
				return RespMessage.fail("已经认证通过，无需重复审批");
			}
		}

		SysUser sysUser = sysUserService.getSysUserByToken(token);

		UserCertLog userCertLog = new UserCertLog();
		BeanUtils.copyProperties(vo, userCertLog);
		userCertLog.setId(UUID.randomUUID().toString());
		userCertLog.setSysUserId(sysUser.getId());
		User updateUser = new User();
		updateUser.setId(vo.getUserId());
		updateUser.setRealnameCertStatus(vo.getResult());
		userMapper.updateByPrimaryKeySelective(updateUser);
		userCertLogMapper.insertSelective(userCertLog);

		Date date = new Date();
		User user = userMapper.selectByPrimaryKey(vo.getUserId());

		if (user!=null) {
			if (vo.getResult() == 0) {
				// 反馈给用户认证通过的消息
				int status = Constant.USER_CERT_SUCCESS;
				String content = "尊敬的用户,您于" + DateUtil.getStringDate(user.getUpdatedAt()) + ",申请的实名认证已通过审核.";
				SysMessageToUser sysMessageToUser = MessageToUserUtil.saveMessage(user.getId(), status, content, date);
				sysMessageToUserMapper.insertSelective(sysMessageToUser);
			} else if (vo.getResult() == 1) {
				// 反馈给用户认证被驳回的理由（消息）
				int status = Constant.USER_CERT_REJECT;
				String content = "尊敬的用户,您于" + DateUtil.getStringDate(user.getUpdatedAt()) + ",申请的实名认证不通过,原因:" + vo.getComment();
				SysMessageToUser sysMessageToUser = MessageToUserUtil.saveMessage(user.getId(), status, content, date);
				sysMessageToUserMapper.insertSelective(sysMessageToUser);
			}
		}
		return RespMessage.success();
	}

	@Override
	public RespMessage list() {
		// TODO Auto-generated method stub
		List<UserCertLog> result = userCertLogMapper.selectAll();
		return RespMessage.success(result);
	}

	@Override
	public RespMessage listMy(String token) {
		// TODO Auto-generated method stub
		SysUser sysUser = sysUserService.getSysUserByToken(token);
		List<UserCertLog> result = userCertLogMapper.selectBySysUserId(sysUser.getId());
		return RespMessage.success(result);
	}



}
