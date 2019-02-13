package com.yale.zc.funding.vo;

import com.yale.zc.funding.bean.CrowdFunding;
import com.yale.zc.funding.bean.FundingLink;

import java.util.List;

/**
 * Created by asus on 2017/7/5.
 */
public class CrowdFundingResultVo {
    private CrowdFundingVo crowdFundingVo;
    private List<FundingLink> urls;

    public CrowdFundingVo getCrowdFundingVo() {
        return crowdFundingVo;
    }

    public void setCrowdFundingVo(CrowdFundingVo crowdFundingVo) {
        this.crowdFundingVo = crowdFundingVo;
    }

    public List<FundingLink> getUrls() {
        return urls;
    }

    public void setUrls(List<FundingLink> urls) {
        this.urls = urls;
    }
}
