package com.yale.zc.funding.service;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.CrowdFunding;
import com.yale.zc.funding.vo.CrowdFundingVo;
import com.yale.zc.funding.vo.PageVo;

/**
 * Created by asus on 2017/12/26.
 */
public interface CrowdFundingService {


    RespMessage add(CrowdFundingVo vo, String token);

    RespMessage update(CrowdFunding vo, String token);

    RespMessage delete(String id, String token);

    RespMessage deleteBatch(String[] ids, String token);

    RespMessage list(PageVo vo);

    RespMessage  queryObject(String id);


}
