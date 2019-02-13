package com.yale.zc.funding.controller;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.Enrolment;
import com.yale.zc.funding.service.EnrolmentService;
import com.yale.zc.funding.vo.EnrolmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author maifayan
 * @email
 * @date 2017-12-29 15:22:13
 */
@RestController
@RequestMapping("/enrolment")
public class EnrolmentController {
    @Autowired
    private EnrolmentService enrolmentService;

    /**
     * 报名众筹
     * */
    @RequestMapping("/add")
    public RespMessage enrolment(EnrolmentVo vo, String token){
        return enrolmentService.add(vo, token);
    }

    /**
     * 众筹信息
     * */
    @RequestMapping("/search")
    public RespMessage enrolmentSearch(String id){return enrolmentService.search(id);}

    /**
     * 用户参与多少个项目
     * */
    @RequestMapping("/findByUser")
    public RespMessage findByUser(String token){
       return enrolmentService.findByUser(token);
    }

    /**
     * 更改众筹信息*/
    @RequestMapping("/update")
    public RespMessage update(EnrolmentVo vo, String token){
        return enrolmentService.update(vo, token);
    }

    /**
     * 删除众筹信息*/
    @RequestMapping("/delete")
    public RespMessage delete(String id,String token){
     return enrolmentService.delete(id, token);
    }
}
