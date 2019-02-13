package com.yale.zc.funding.controller;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.MyAttention;
import com.yale.zc.funding.service.MyAttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author maifayan
 * @email
 * @date 2017-12-29 8:47:13
 */
@RestController
@RequestMapping("/attention")
public class AttentionController {
    @Autowired
    private MyAttentionService myAttentionService;

    /**
     * 关注
     * */
    @RequestMapping("/add")
    public RespMessage addAttention(String fundingId, String token){
        return myAttentionService.add(fundingId, token);
    }

    /**
     * 更新关注
     * */
    @RequestMapping("/update")
    public RespMessage updateAttention(MyAttention vo){
        return myAttentionService.update(vo);
    }

    /**
     * 排列所有关注的内容
     * */
    @RequestMapping("/findByUser")
    public RespMessage findAttention(String token){
        return myAttentionService.findByUser(token);
    }

    /**
     * 是否关注*/
    @RequestMapping("/attention")
    public RespMessage attention(String token){return myAttentionService.attention(token);}
}
