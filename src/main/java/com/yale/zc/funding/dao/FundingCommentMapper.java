package com.yale.zc.funding.dao;

import com.yale.zc.funding.bean.FundingComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FundingCommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(FundingComment record);

    int insertSelective(FundingComment record);

    FundingComment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FundingComment record);

    int updateByPrimaryKey(FundingComment record);

    List<FundingComment> findByEnrolment(@Param("fundingId")String fundingId, @Param("enrolmentId")String enrolmentId);
}