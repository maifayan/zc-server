package com.yale.zc.core.config;

/**
 * Created by asus on 2017/6/29.
 */
public class Constant {

    // 审核
    public static final int AUDIT_STATUS_OK = 0;            // 通过
    public static final int AUDIT_STATUS_FAIL = 1;          // 驳回
    public static final int AUDIT_STATUS_UNSUBMITTED = 2;   // 未提交
    public static final int AUDIT_STATUS_SUBMITTED = 3;     // 已提交待审核
    public static final int AUDIT_STATUS_DONE = 4;     // 佣金已支付,办结

    // 手续费
    public static final int COMMISSION_TYPE_FIXED = 0;      // 固定金额手续费
    public static final int COMMISSION_TYPE_PERCENT = 1;    // 固定百分比手续费

    // 订单
    public static final int ORDER_DEFAULT = 0;              // 未支付（默认）
    public static final int ORDER_PAID = 1;                 // 已支付
    public static final int ORDER_IN_PROGRESS = 2;          // 办理中
    public static final int ORDER_FINISHED = 3;             // 经纪人确认办结
    public static final int ORDER_COMPLETED = 4;             // 用户确认
    public static final int ORDER_CASHOUT_REQUEST = 5;             // 结佣申请
    public static final int ORDER_CASHOUT_CONFIRMED = 6;            // 结佣审核通过
    public static final int ORDER_CASHOUTED = 7;            // 结佣支付完成
    public static final int ORDER_LOCK = 8;                 //失效(用户超时支付或用户取消或退款成功)

    //退款


    public static final int HOUSE_NORMAL = 0;               // 初始状态
    public static final int HOUSE_OFF_THE_SHELF = 1;        // 房源下架
    public static final int HOUSE_DELETE = 2;              // 房源假删除
    public static final int HOUSE_DISABLE_OPERATION = 3;    // 管理员对房源投诉处理   封杀该房源

    public static final int HOUSE_HIDE = 2;                 // 房源隐藏
    public static final int HOUSE_TOP = 3;                 // 房源置顶

    public static final String SYS_ROLE_ADMIN = "管理员";                 // 房源置顶

    public static final int DISABLE_AGENT = 4;            //禁用经纪人
    public static final int RECOVERY_AGENT = 0;           //解除经纪人的禁用

    // 针对sys_message_to_user这张表的分类状态
    public static final int USER_REFUND_SUCCESS = 0;      // 用户退款通过
    public static final int USER_REFUND_REJECT = 7;       // 用户退款驳回
    public static final int AGENT_WITHDRAWALS_SUCCESS = 1;// 经纪人提现通过
    public static final int AGENT_WITHDRAWALS_REJECT = 6; // 经纪人提现驳回
    public static final int AGETN_CERT_REJECT = 2;        // 记录经纪人认证驳回
    public static final int AGENT_CERT_SUCCESS = 3;       // 记录经纪人认证通过
    public static final int USER_CERT_SUCCESS = 4;        // 用户认证通过
    public static final int USER_CERT_REJECT = 5;         // 用户认证驳回
    public static final int HOUSE_CERT_SUCCESS = 8;       // 房源认证通过
    public static final int HOUSE_CERT_REJECT = 9;        // 房源认证驳回
    public static final int HOUSE_COMPLAINT_SUCCESS = 10;  // 房源投诉通过
    public static final int HOUSE_COMPLAINT_REJECT = 11;

    public static final String AGENT_WITHDRAWALS_MESSAGE = "房公信已经提现到您的账户中,订单的提现金额为:";
    public static final String AGENT_WITHDRAWALS_REJECT_MESSAGE = "房公信通知您,您的申请提现不符合标准,已经驳回";

    public static final String USER_REFUND_MESSAGE = "房公信已经退款到您的账户中,订单的退款金额为:";
    public static final String USER_REFUND_REJECT_MESSAGE = "房公信通知您,您的申请退款不符合标准,已经驳回";

    public static final String AGENT_REJECT_MESSAGE = "房公信通知您,你的经纪人申请不符合标准,已经被驳回";
    public static final String AGENT_CERT_SUCCESS_MESSAGE = "房公信通知您,你的经纪人申请符合标准,已经通过";

    public static final String USER_CERT_SUCCESS_MESSAGE = "房公信通知您,您的用户认证申请符合标准,已经通过";
    public static final String USER_REJECT_MESSAGE = "房公信通知您,您的用户认证申请不符合标准,已经被驳回";

    public static final String HOUSE_CERT_SUCCESS_MESSAGE = "房公信通知您,您的房源认证申请符合标准,已经通过";
    public static final String HOUSE_REJECT_MESSAGE = "房公信通知您,您的房源认证申请不符合标准,已经被驳回";

}