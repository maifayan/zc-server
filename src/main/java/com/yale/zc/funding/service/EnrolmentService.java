package com.yale.zc.funding.service;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.Enrolment;
import com.yale.zc.funding.vo.EnrolmentVo;

/**
 *
 *
 * @author maifayan
 * @email
 * @date 2017-12-29 15:38:13
 */
public interface EnrolmentService {

    RespMessage add(EnrolmentVo vo, String token);

    RespMessage search(String id);

    RespMessage findByUser(String token);

    RespMessage update(EnrolmentVo vo, String token);

    RespMessage delete(String id, String token);

}
