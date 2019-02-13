package com.yale.zc.pay.dao;

import com.yale.zc.pay.bean.PaymentNumber;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentNumberMapper {
    int deleteByPrimaryKey(String id);

    int insert(PaymentNumber record);

    int insertSelective(PaymentNumber record);

    PaymentNumber selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PaymentNumber record);

    int updateByPrimaryKey(PaymentNumber record);
}