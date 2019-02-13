package com.yale.zc.funding.vo;

import com.yale.zc.funding.bean.CrowdFunding;
import com.yale.zc.funding.bean.Enrolment;
import com.yale.zc.funding.bean.FundingComment;
import com.yale.zc.funding.bean.SupportItem;

import java.util.List;

/**
 *
 *
 * @author maifayan
 * @email
 * @date 2018-1-3 11:45:13
 */
public class FundingQueryResultVo {
    private CrowdFunding crowdFunding;
    private EnrolmentVo enrolmentVo;
    private List<FundingComment> fundingComments;
    private List<SupportItem> supportItems;


    public CrowdFunding getCrowdFunding() {
        return crowdFunding;
    }

    public void setCrowdFunding(CrowdFunding crowdFunding) {
        this.crowdFunding = crowdFunding;
    }


    public List<SupportItem> getSupportItems() {
        return supportItems;
    }

    public void setSupportItems(List<SupportItem> supportItems) {
        this.supportItems = supportItems;
    }

    public List<FundingComment> getFundingComments() {
        return fundingComments;
    }

    public void setFundingComments(List<FundingComment> fundingComments) {
        this.fundingComments = fundingComments;
    }

    public EnrolmentVo getEnrolmentVo() {
        return enrolmentVo;
    }

    public void setEnrolmentVo(EnrolmentVo enrolmentVo) {
        this.enrolmentVo = enrolmentVo;
    }
}
