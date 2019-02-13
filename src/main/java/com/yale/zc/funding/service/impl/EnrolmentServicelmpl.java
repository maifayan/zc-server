package com.yale.zc.funding.service.impl;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.CrowdFunding;
import com.yale.zc.funding.bean.Enrolment;
import com.yale.zc.funding.bean.FundingComment;
import com.yale.zc.funding.bean.SupportItem;
import com.yale.zc.funding.dao.CrowdFundingMapper;
import com.yale.zc.funding.dao.EnrolmentMapper;
import com.yale.zc.funding.dao.FundingCommentMapper;
import com.yale.zc.funding.dao.SupportItemMapper;
import com.yale.zc.funding.service.EnrolmentService;
import com.yale.zc.funding.vo.EnrolmentVo;
import com.yale.zc.funding.vo.FundingQueryResultVo;
import com.yale.zc.user.bean.User;
import com.yale.zc.user.service.UserService;
import com.yale.zc.user.vo.UserRedisVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;

/**
 *
 *
 * @author maifayan
 * @email
 * @date 2017-12-29 16:06:13
 */
@Service
public class EnrolmentServicelmpl implements EnrolmentService{
    @Autowired
    EnrolmentMapper enrolmentMapper;

    @Autowired
    CrowdFundingMapper crowdFundingMapper;

    @Autowired
    SupportItemMapper supportItemMapper;

    @Autowired
    FundingCommentMapper fundingCommentMapper;

    @Autowired
    UserService userService;

    @Override
    public RespMessage add(EnrolmentVo vo, String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo==null){
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return  RespMessage.fail("用户不存在");
        }
        Enrolment enrolment = new Enrolment();
        BeanUtils.copyProperties(vo, enrolment);
        enrolment.setId(UUID.randomUUID().toString());
        enrolmentMapper.insert(enrolment);
        return RespMessage.success();
    }

    @Override
    public RespMessage search(String id) {
        Enrolment enrolment = enrolmentMapper.selectByPrimaryKey(id);//报名
        CrowdFunding crowdFunding = crowdFundingMapper.queryObject(enrolment.getFundingId());//众筹
        List<SupportItem> supportItems = supportItemMapper.findByUser(enrolment.getUserId(),enrolment.getFundingId());//支持者
        List<FundingComment> fundingComments  = fundingCommentMapper.findByEnrolment(enrolment.getFundingId(),enrolment.getId());//评论
        EnrolmentVo enrolmentVo =new EnrolmentVo();
        //这是复制：左边赋值给右边
        BeanUtils.copyProperties(enrolment, enrolmentVo);
        double sum = 0;
        for (int i = 0; i <supportItems.size() ; i++) {
            double  amount = supportItems.get(i).getAmount();
            sum += amount;
        }
        enrolmentVo.setRaiseAmount(sum);
        enrolmentVo.setSupportNumber(supportItems.size());
        FundingQueryResultVo fundingQueryResultVo = new FundingQueryResultVo();
        fundingQueryResultVo.setCrowdFunding(crowdFunding);
        fundingQueryResultVo.setEnrolmentVo(enrolmentVo);
        fundingQueryResultVo.setSupportItems(supportItems);
        fundingQueryResultVo.setFundingComments(fundingComments);

        return RespMessage.success(fundingQueryResultVo);
    }


    @Override
    public RespMessage findByUser(String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo==null){
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return  RespMessage.fail("用户不存在");
        }
        String userId = user.getId();
        List<Enrolment> enrolments =  enrolmentMapper.findByUser(userId);
        return RespMessage.success(enrolments);
    }

    @Override
    public RespMessage update(EnrolmentVo vo, String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo==null){
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return  RespMessage.fail("用户不存在");
        }
        Enrolment enrolment = new Enrolment();
        BeanUtils.copyProperties(vo, enrolment);
        enrolmentMapper.updateByPrimaryKeySelective(enrolment);
        return RespMessage.success();
    }

    @Override
    public RespMessage delete(String id, String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo==null){
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return  RespMessage.fail("用户不存在");
        }
        enrolmentMapper.deleteByPrimaryKey(id);
        return RespMessage.success();
    }

}
