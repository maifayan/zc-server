package com.yale.zc.system.dao;

import com.yale.zc.system.bean.SysModule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysModuleMapper {
    int deleteByPrimaryKey(String id);

    void insert(SysModule record);

    void insertSelective(SysModule record);

    SysModule selectByPrimaryKey(String id);

    void updateByPrimaryKeySelective(SysModule record);

    void updateByPrimaryKey(SysModule record);

    SysModule selectByModuleName(String name);
    
    List<SysModule> selectAll();

	void changeModuleStatusByPrimaryKey(SysModule sysModule);

    List<SysModule> selectSelective(SysModule sysModule);


}