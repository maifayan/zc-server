package com.yale.zc.user.dao;

import com.yale.zc.user.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User selectByPhone(String phone);

	List<User> selectByRealnameCertStatus(Integer status);

    List<User> selectAll();

    List<User> selectByAgentCertStatus();


}