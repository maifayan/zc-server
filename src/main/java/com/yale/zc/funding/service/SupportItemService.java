package com.yale.zc.funding.service;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.SupportItem;



/**
 * Created by WQHJD on 2018/1/3.
 */
public interface SupportItemService {
    RespMessage add(SupportItem vo, String token);

    RespMessage findByUser(String fundingId, String token);

}
