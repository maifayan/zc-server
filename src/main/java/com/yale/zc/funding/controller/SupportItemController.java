package com.yale.zc.funding.controller;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.SupportItem;
import com.yale.zc.funding.service.SupportItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author maifayan
 * @email
 * @date 2018-1-3 9:30:13
 */
@RestController
@RequestMapping("/supportitem")
public class SupportItemController {

    @Autowired
    SupportItemService supportItemService;

    /**
     * 添加支持者*/
    @RequestMapping("/add")
    public RespMessage add(SupportItem vo, String token){return  supportItemService.add(vo, token);
    }

    /**
     * 查找支持者*/
    @RequestMapping("/findUser")
    public RespMessage findByUser(String fundingId, String token){return supportItemService.findByUser(fundingId, token);}
}
