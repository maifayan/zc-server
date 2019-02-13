package com.yale.zc.funding.service;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.FundingComment;

/**
 * Created by WQHJD on 2018/1/2.
 */
public interface FundingCommentService {
    RespMessage add(FundingComment vo, String token);

    RespMessage delete(String id, String token);

    RespMessage findByEnrolment(String fundingId, String enrolmentId);

}
