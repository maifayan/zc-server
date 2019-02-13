package com.yale.zc.funding.dao;

import com.yale.zc.funding.bean.MyAttention;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyAttentionMapper {
    int deleteByPrimaryKey(String id);

    int insert(MyAttention record);

    int insertSelective(MyAttention record);

    List<MyAttention> selectByPrimaryKey(String id);

    List<MyAttention> selectByUserId(String userId);

    int updateByPrimaryKeySelective(MyAttention record);

    int updateByPrimaryKey(MyAttention record);


}