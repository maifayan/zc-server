package com.yale.zc.user.dao;

import com.yale.zc.user.bean.UserWx;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserWxMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserWx record);

    int insertSelective(UserWx record);

    UserWx selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserWx record);

    int updateByPrimaryKey(UserWx record);

	UserWx selectByOpenId(String openid);

    UserWx selectByUserId(String userId);
}