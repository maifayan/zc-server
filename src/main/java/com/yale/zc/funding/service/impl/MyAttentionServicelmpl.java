package com.yale.zc.funding.service.impl;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.CrowdFunding;
import com.yale.zc.funding.bean.MyAttention;
import com.yale.zc.funding.dao.CrowdFundingMapper;
import com.yale.zc.funding.dao.MyAttentionMapper;
import com.yale.zc.funding.service.MyAttentionService;
import com.yale.zc.user.bean.User;
import com.yale.zc.user.service.UserService;
import com.yale.zc.user.vo.UserRedisVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 *
 * @author maifayan
 * @email
 * @date 2017-12-29 9:22:13
 */
@Service
public class MyAttentionServicelmpl implements MyAttentionService{
    int STATUS_ATTENTION = 1;//关注

    @Autowired
    MyAttentionMapper myAttentionMapper;

    @Autowired
    CrowdFundingMapper crowdFundingMapper;

    @Autowired
    UserService userService;

    @Override
    public RespMessage add(String fundingId, String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400,"登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        String userId = user.getId();
        MyAttention myAttention = new MyAttention();
        myAttention.setId(UUID.randomUUID().toString());
        myAttention.setFundingId(fundingId);
        myAttention.setUserId(userId);
        myAttention.setStatus(1);
        myAttentionMapper.insert(myAttention);
        return RespMessage.success();
    }

    @Override
    public RespMessage update(MyAttention vo) {
        myAttentionMapper.updateByPrimaryKeySelective(vo);
        return RespMessage.success();
    }

    @Override
    public RespMessage findByUser(String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400,"登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        String userId = user.getId();
        List<MyAttention> myAttentions =  myAttentionMapper.selectByUserId(userId);
        List<CrowdFunding> crowdFundings = new ArrayList<>();
        //遍历my_attention
        for (int i = 0; i < myAttentions.size(); i++) {
            MyAttention attention = myAttentions.get(i);
            if (attention.getStatus() == STATUS_ATTENTION) {//是否myattention字段status是否为1；"1".equals(String.valueOf(attention.getStatus()))
                CrowdFunding crowdFunding =  crowdFundingMapper.queryObject(attention.getFundingId());
                crowdFundings.add(crowdFunding);
            }
        }
        return RespMessage.success(crowdFundings);
    }

    @Override
    public RespMessage attention(String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400,"登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        String userId = user.getId();
        List<MyAttention> myAttentions =  myAttentionMapper.selectByUserId(userId);
        return RespMessage.success(myAttentions);
    }
}
