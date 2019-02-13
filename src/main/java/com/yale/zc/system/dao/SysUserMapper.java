package com.yale.zc.system.dao;

import com.yale.zc.system.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    void deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    void updateByPrimaryKeySelective(SysUser user); 

    void updateByPrimaryKey(SysUser user);

	SysUser selectByAccount(String account);

	SysUser checkExistUserAccountByUserId(SysUser sysUser);

	void updateAccountInvalidByPrimeryKey(SysUser deleteUser);

	List<SysUser> selectAllUserOrderByStatus();

	List<SysUser> selectSelective(SysUser sysUser);

    List<SysUser> selectAll(SysUser sysUser);
}