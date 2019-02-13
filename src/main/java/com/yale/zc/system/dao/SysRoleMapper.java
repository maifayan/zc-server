package com.yale.zc.system.dao;

import com.yale.zc.system.bean.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
	void deleteByPrimaryKey(String id);

    void insert(SysRole record);

    void insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    void updateByPrimaryKeySelective(SysRole record);

    void updateByPrimaryKey(SysRole record);

	SysRole selectByRoleName(String name);

	List<SysRole> selectAll();

	List<SysRole> selectSelective(SysRole role);

	SysRole confirmRoleName(SysRole role);

	void updateRoleMapper(SysRole sysRole);
}