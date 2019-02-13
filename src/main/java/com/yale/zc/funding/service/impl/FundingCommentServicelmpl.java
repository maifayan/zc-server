package com.yale.zc.funding.service.impl;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.FundingComment;
import com.yale.zc.funding.dao.FundingCommentMapper;
import com.yale.zc.funding.service.FundingCommentService;
import com.yale.zc.user.bean.User;
import com.yale.zc.user.service.UserService;
import com.yale.zc.user.vo.UserRedisVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by WQHJD on 2018/1/2.
 */
@Service
public class FundingCommentServicelmpl implements FundingCommentService{

    @Autowired
    FundingCommentMapper fundingCommentMapper;

    @Autowired
    UserService userService;

    @Override
    public RespMessage add(FundingComment vo, String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400,"登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        vo.setId(UUID.randomUUID().toString());
        fundingCommentMapper.insertSelective(vo);
        return RespMessage.success();
    }

    @Override
    public RespMessage delete(String id, String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400,"登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        fundingCommentMapper.deleteByPrimaryKey(id);
        return RespMessage.success();
    }

    @Override
    public RespMessage findByEnrolment(String fundingId, String enrolmentId) {
      List<FundingComment> fundingComments = fundingCommentMapper.findByEnrolment(fundingId, enrolmentId);
        return RespMessage.success(fundingComments);
    }
}
