package com.yale.zc.funding.controller;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.FundingComment;
import com.yale.zc.funding.service.FundingCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author maifayan
 * @email
 * @date 2018-1-2 14:27:13
 */
@RestController
@RequestMapping("/fundingcomment")
public class FundingCommentController {
    @Autowired
    private FundingCommentService fundingCommentService;

    /**
     * 添加评论或回复*/
    @RequestMapping("/add")
    public RespMessage add(FundingComment vo, String token){return fundingCommentService.add(vo, token);}

    /**
     * 删除评论或回复*/
    @RequestMapping("/delete")
    public RespMessage delete(String id, String token){return  fundingCommentService.delete(id, token);}



    /**
     * 查找评论或回复*/
    @RequestMapping("/findByEnrolment")
    public RespMessage findByUserId(String fundingId, String enrolmentId){return  fundingCommentService.findByEnrolment(fundingId, enrolmentId);}

}
