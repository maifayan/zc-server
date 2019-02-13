package com.yale.zc.funding.dao;

import com.yale.zc.funding.bean.Enrolment;
import com.yale.zc.funding.vo.EnrolmentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EnrolmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Enrolment record);

    int insertSelective(Enrolment record);

    Enrolment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Enrolment record);

    int updateByPrimaryKey(Enrolment record);

    List<Enrolment> findByUser(String userId);
}