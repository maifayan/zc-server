package com.yale.zc.funding.service;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.MyAttention;


/**
 * Created by WQHJD on 2017/12/28.
 */
public interface MyAttentionService {
    RespMessage add(String fundingId, String token);

    RespMessage update(MyAttention vo);

    RespMessage findByUser(String token);

    RespMessage attention(String token);

}
