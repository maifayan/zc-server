package com.yale.zc.system.dao;


import com.yale.zc.system.bean.SysMessageToUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMessageToUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysMessageToUser record);

    int insertSelective(SysMessageToUser record);

    SysMessageToUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysMessageToUser record);

    int updateByPrimaryKey(SysMessageToUser record);

    List<SysMessageToUser> selectByUserId(String userId);
}