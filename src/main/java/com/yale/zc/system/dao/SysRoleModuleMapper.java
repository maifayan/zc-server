package com.yale.zc.system.dao;

import com.yale.zc.system.bean.SysRoleModule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleModuleMapper {
    void insert(SysRoleModule record);

    void insertSelective(SysRoleModule record);
    
    List<SysRoleModule> selectByRoleId(String roleId);

	void deleteByRoleId(String roleId);
	
	List<SysRoleModule> selectByMoudleId(String moduleId);

}