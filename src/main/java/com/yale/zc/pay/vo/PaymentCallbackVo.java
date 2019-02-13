package com.yale.zc.pay.vo;

/**
 * Created by asus on 2017/7/6.
 */
public class PaymentCallbackVo {
    private Long amount; // 订单总金额，单位：分
    private Long receive_time;  //	true	timestamp	付钱拉接收商户的订单时间，20160602151259
    private Long complete_time; //	true	timestamp	付钱拉成功处理三方异步通知时间，20160602151259
    private String merch_id;    //	true	string(11)	商户在付钱拉的编号
    private String charge_id;   //	true	string(32)	系统单号
    private String order_no;    //	true	string(32)	商户单号
    private String ret_code;    //	false	string(64)	订单状态，只会通知订单最终状态
    private String ret_info;	//  false	string(256)	返回具体的错误描述
    private String optional;	//  false	string(512)	附加数据。用户自定义的参数，将会在通知中原样返回，该字段主要用于商户携带订单的自定义数据
    private String version;	    //  false	string(10)	服务端接口版本号,与请求时一致，如：v2.1.1
    private String sign_type;	//  true	string(10)	与支付请求时一致 （RSA或MD5）
    private String sign_info;	//  true	string(254)	签名信息

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(Long receive_time) {
        this.receive_time = receive_time;
    }

    public Long getComplete_time() {
        return complete_time;
    }

    public void setComplete_time(Long complete_time) {
        this.complete_time = complete_time;
    }

    public String getMerch_id() {
        return merch_id;
    }

    public void setMerch_id(String merch_id) {
        this.merch_id = merch_id;
    }

    public String getCharge_id() {
        return charge_id;
    }

    public void setCharge_id(String charge_id) {
        this.charge_id = charge_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_info() {
        return ret_info;
    }

    public void setRet_info(String ret_info) {
        this.ret_info = ret_info;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign_info() {
        return sign_info;
    }

    public void setSign_info(String sign_info) {
        this.sign_info = sign_info;
    }

    @Override
    public String toString() {
        return "PaymentCallbackVo{" +
                "amount=" + amount +
                ", receive_time=" + receive_time +
                ", complete_time=" + complete_time +
                ", merch_id='" + merch_id + '\'' +
                ", charge_id='" + charge_id + '\'' +
                ", order_no='" + order_no + '\'' +
                ", ret_code='" + ret_code + '\'' +
                ", ret_info='" + ret_info + '\'' +
                ", optional='" + optional + '\'' +
                ", version='" + version + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", sign_info='" + sign_info + '\'' +
                '}';
    }
}
