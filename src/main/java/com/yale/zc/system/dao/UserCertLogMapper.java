package com.yale.zc.system.dao;

import com.yale.zc.system.bean.UserCertLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCertLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserCertLog record);

    int insertSelective(UserCertLog record);

    UserCertLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserCertLog record);

    int updateByPrimaryKey(UserCertLog record);

    List<UserCertLog> selectAll();

    List<UserCertLog> selectBySysUserId(String sysUserId);

    List<UserCertLog> selectByUserId(String userId);
}