package com.yale.zc.pay.dao;

import com.yale.zc.pay.bean.FqlPaymentLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FqlPaymentLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(FqlPaymentLog record);

    int insertSelective(FqlPaymentLog record);

    FqlPaymentLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FqlPaymentLog record);

    int updateByPrimaryKey(FqlPaymentLog record);

    FqlPaymentLog selectByOrderNo(String orderNo);

}