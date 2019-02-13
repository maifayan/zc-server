package com.yale.zc.pay.service.impl;

import com.yale.zc.core.config.Constant;
import com.yale.zc.core.util.RespMessage;

import com.yale.zc.pay.bean.FqlPaymentLog;
import com.yale.zc.pay.bean.PaymentNumber;
import com.yale.zc.pay.dao.FqlPaymentLogMapper;
import com.yale.zc.pay.dao.PaymentNumberMapper;
import com.yale.zc.pay.service.PayService;
import com.yale.zc.pay.vo.PaymentNumberVo;
import com.yale.zc.system.bean.UserPaymentLog;
import com.yale.zc.system.dao.UserPaymentLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenmingfa on 2017/7/11.
 */
@Service
public class PayServiceImpl implements PayService {


    @Autowired
    FqlPaymentLogMapper fqlPaymentLogMapper;

    @Autowired
    UserPaymentLogMapper userPaymentLogMapper;


    @Autowired
    PaymentNumberMapper paymentNumberMapper;

/*    @Override
    public RespMessage query(String orderId) {
        //验证是否支付
        AgentOrder order = agentOrderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            return RespMessage.fail(400, "订单号不存在");
        }
        FqlPaymentLog fqlLog = fqlPaymentLogMapper.selectByOrderNo(order.getOrderNo().toString());
        if (fqlLog == null) {
            return RespMessage.fail(400, "支付记录不存在");
        }

        UserPaymentLog paymentLog = userPaymentLogMapper.selectByOrderId(order.getId());
        if (paymentLog == null) {
            return RespMessage.fail(400, "支付记录不存在");
        }

        if (order.getStatus() == Constant.ORDER_PAID) {
            return RespMessage.success();
        }
        //修改房子为隐藏状态
      //  House house = houseMapper.selectByPrimaryKey(order.getHouseId());

        House updateHouse = new House();
        updateHouse.setId(order.getHouseId());
        updateHouse.setStatus(Constant.HOUSE_HIDE);
        houseMapper.updateByPrimaryKeySelective(updateHouse);

        return RespMessage.fail(400, "支付数据异常");
    }

    @Override
    public RespMessage getPaymentNumber(String orderNo) {
        PaymentNumber paymentNumber = new PaymentNumber();
        String payNo = snowflakeIdWorker.nextId() + "";
        paymentNumber.setId(payNo);
        paymentNumber.setOrderNo(Long.parseLong(orderNo));
        paymentNumberMapper.insertSelective(paymentNumber);
        PaymentNumberVo vo = new PaymentNumberVo();
        vo.setPayNo(payNo);
        vo.setOrderNo(orderNo);
        return RespMessage.success(vo);
    }*/
}
