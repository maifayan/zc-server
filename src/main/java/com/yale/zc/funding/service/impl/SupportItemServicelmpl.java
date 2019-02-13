package com.yale.zc.funding.service.impl;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.SupportItem;
import com.yale.zc.funding.dao.SupportItemMapper;
import com.yale.zc.funding.service.SupportItemService;

import com.yale.zc.user.bean.User;
import com.yale.zc.user.service.UserService;
import com.yale.zc.user.vo.UserRedisVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by WQHJD on 2018/1/3.
 */
@Service
public class SupportItemServicelmpl implements SupportItemService {

    @Autowired
    SupportItemMapper supportItemMapper;

    @Autowired
    UserService userService;

    @Override
    public RespMessage add(SupportItem vo, String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo==null){
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return  RespMessage.fail("用户不存在");
        }
        vo.setId(UUID.randomUUID().toString());
        supportItemMapper.insert(vo);
        return RespMessage.success();
    }

    @Override
    public RespMessage findByUser(String fundingId, String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo==null){
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return  RespMessage.fail("用户不存在");
        }
        String userId = user.getId();
        List<SupportItem> supportItems = supportItemMapper.findByUser(userId,fundingId);
        return RespMessage.success(supportItems);
    }
}
