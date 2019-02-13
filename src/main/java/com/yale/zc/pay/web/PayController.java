package com.yale.zc.pay.web;

import com.yale.zc.core.config.Constant;
import com.yale.zc.core.util.RespMessage;
import com.yale.zc.pay.bean.FqlPaymentLog;
import com.yale.zc.pay.bean.PaymentNumber;
import com.yale.zc.pay.dao.FqlPaymentLogMapper;
import com.yale.zc.pay.dao.PaymentNumberMapper;
import com.yale.zc.pay.service.PayService;
import com.yale.zc.pay.vo.PaymentCallbackVo;
import com.yale.zc.system.bean.UserPaymentLog;
import com.yale.zc.system.dao.UserPaymentLogMapper;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 支付业务
 * Created by asus on 2017/7/6.
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);

    @Autowired
    FqlPaymentLogMapper fqlPaymentLogMapper;

    @Autowired
    UserPaymentLogMapper userPaymentLogMapper;


    @Autowired
    PayService payService;

    @Autowired
    PaymentNumberMapper paymentNumberMapper;

//    /**
//     * 查询是否支付
//     * @param orderId
//     * @return
//     */
//    @RequestMapping("/query")
//    public RespMessage query(String orderId) {
//        return payService.query(orderId);
//    }

//    /**
//     * 获取支付号
//     * @param orderNo
//     * @return
//     */
//    @RequestMapping("/get-payment-number")
//    public RespMessage getPaymentNumber(String orderNo) {
//        return payService.getPaymentNumber(orderNo);
//    }


    /**
     * 接收付钱拉通知入口
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/notify")
    public void handleNotify(HttpServletRequest request, HttpServletResponse response) throws IOException{
        LOGGER.info("接收付钱拉通知入口" );
        String msg = request.getParameter("msg");
        String resMsg = "fail";
        if(!"".equals(msg) && msg !=null){
            LOGGER.info("#接收付钱拉通知内容:"+msg);
         //   System.out.println(String.format("接收付钱拉通知内容 msg\n%s", msg));
            // 随机返回成功失败、测试使用
            String[] str = { "SUCCESS", "SUCCESS", "SUCCESS", "FAIL", "SUCCESS" };
            int random = (int) (Math.random() * 5);
            resMsg =  str[random];
        }else{
            String requestStr = readReqStr(request);
            LOGGER.info("###接收付钱拉通知内容:"+requestStr);
           // System.out.println(String.format("接收付钱拉通知内容\n%s", requestStr));
            try {
                @SuppressWarnings("unchecked")
                Map<String,Object> objMap = (Map<String, Object>) JSON.parse(requestStr);
                PaymentCallbackVo vo = JSON.parseObject(requestStr, PaymentCallbackVo.class);
                LOGGER.info("#PaymentCallbackVo:"+vo.toString());

                boolean flag = verify(objMap);
                LOGGER.info("#flag:"+flag);
                if(flag){
                    LOGGER.info("签名验证通过");
                    // 写入数据库
                    FqlPaymentLog log = new FqlPaymentLog();
                    log.setId(UUID.randomUUID().toString());
                    log.setAmount(vo.getAmount());
                    log.setChargeId(vo.getCharge_id());
                    log.setCompleteTime(vo.getComplete_time());
                    log.setMerchId(vo.getMerch_id());
                    log.setOptional(vo.getOptional());
                    // 支付号 非订单号 为了解决微信支付不能重复的问题20170816
                    log.setOrderNo(vo.getOrder_no());
                    log.setReceiveTime(vo.getReceive_time());
                    log.setRetCode(vo.getRet_code());
                    log.setRetInfo(vo.getRet_info());
                    log.setSignInfo(vo.getSign_info());
                    log.setSignType(vo.getSign_type());
                    log.setVersion(vo.getVersion());
                    fqlPaymentLogMapper.insertSelective(log);

                    // 校验订单
                    // 用支付号换取订单号
                    PaymentNumber paymentNumber = paymentNumberMapper.selectByPrimaryKey(vo.getOrder_no());
            /*        if (paymentNumber != null) {
                        AgentOrder order = agentOrderMapper.selectByOrderNo(paymentNumber.getOrderNo());
                        if (order != null && (order.getAmount() * 100) == vo.getAmount()) {
                            UserPaymentLog userPaymentLog = new UserPaymentLog();
                            userPaymentLog.setId(UUID.randomUUID().toString());
                            userPaymentLog.setOrderNo(Long.valueOf(vo.getOrder_no()));
                            userPaymentLog.setOrderId(order.getId());
                            userPaymentLog.setPlatform(0);
                            userPaymentLog.setPlatformNumber(vo.getCharge_id());
                            userPaymentLog.setPlatformStatus(vo.getRet_code());
                            userPaymentLog.setUserId(order.getUserId());

                            userPaymentLogMapper.insertSelective(userPaymentLog);
                            // 修改订单的状态
                             AgentOrder updateOrder = new AgentOrder();
                            updateOrder.setId(order.getId());
                            updateOrder.setStatus(Constant.ORDER_PAID);
                             agentOrderMapper.updateByPrimaryKeySelective(updateOrder);
                        }
                    }*/
                    LOGGER.info("#写入数据库成功");
                    resMsg = "success";
                }
            } catch (Exception e) {
                LOGGER.info("通知报文不合法，解析请求报文error:"+e);
            }
        }
//        System.out.println("接收付钱拉通知后响应信息,"+resMsg);
        response.setContentType("text/html");
        response.getWriter().write(resMsg);

    }
    /**
     * 请求转成string
     * @param request
     * @return
     */
    public static String readReqStr(HttpServletRequest request){
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try{
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
//            System.out.println(e);
        }finally{
            try {
                if (null != reader){
                    reader.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 验证接收付钱拉通知签名入口
     * @param objMap
     * @return
     * @throws Exception
     */
    public boolean verify(Map<String, ?> objMap) {
        boolean flag = false;
        // String signKey =    "d2d55ca892664009b8b8be550b1c1da9";
        String signKey = "8BB418FCA8A480BC3E00365AE14148A2";
        try {
            String hexSign = String.valueOf(objMap.get("sign_info"));
            // 得到待签名数据
            Map<String, ?> filterMap = paraFilter(objMap);
            String linkStr = createLinkString(filterMap);
            // 拼装md5串  md5.linkStr
            String templinkStr = linkStr + "&key=" + signKey;
            String md5Hex = DigestUtils.md5DigestAsHex(templinkStr.getBytes("UTF-8"));
            // 验证签名后数据是否相同
            flag = hexSign.equalsIgnoreCase(md5Hex);
        } catch (Exception e) {
            //验证通知签名信息error
        }
        return flag;

    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, ?> paraFilter(Map<String, ?> sArray) {
        Map<String, Object> result = new HashMap<String, Object>();
        if ((sArray == null) || (sArray.size() <= 0)) {
            return result;
        }
        for (String key : sArray.keySet()) {
            Object value = sArray.get(key);
            if ((value == null) || value.equals("") || key.equalsIgnoreCase("sign_info")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, ?> m = (Map<String, ?>) value;
                result.put(key, paraFilter(m));
            } else if (value instanceof List) {
                continue;// 不应包含多集合数据
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, ?> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuffer prestr = new StringBuffer("");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object o = params.get(key);
            String value = String.valueOf(o);
            if (o instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, ?> m = (Map<String, ?>) o;
                value = "{" + createLinkString(m) + "}";
            }

            if (i == (keys.size() - 1)) {// 拼接时，不包括最后一个&字符
                prestr.append(key + "=" + value);
            } else {
                prestr.append(key + "=" + value + "&");
            }
        }
        return prestr.toString();
    }
}
