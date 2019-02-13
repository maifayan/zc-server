package com.yale.zc.system.dao;

import com.yale.zc.system.bean.UserPaymentLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPaymentLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserPaymentLog record);

    int insertSelective(UserPaymentLog record);

    UserPaymentLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserPaymentLog record);

    int updateByPrimaryKey(UserPaymentLog record);

    UserPaymentLog selectByOrderId(String orderId);
}