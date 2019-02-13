package com.yale.zc.user.dao;

import com.yale.zc.user.bean.UserDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserDetail record);

    int insertSelective(UserDetail record);

    UserDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserDetail record);

    int updateByPrimaryKey(UserDetail record);

    UserDetail selectByUserId(String userId);
}