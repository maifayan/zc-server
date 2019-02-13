package com.yale.zc.funding.dao;

import com.yale.zc.funding.bean.SupportItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SupportItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(SupportItem record);

    int insertSelective(SupportItem record);

    SupportItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SupportItem record);

    int updateByPrimaryKey(SupportItem record);

    List<SupportItem> findByUser(@Param("userId")String userId, @Param("fundingId")String fundingId);

}