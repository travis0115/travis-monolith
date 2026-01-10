package com.travis.monolith.module.demo.internal.constant;

/**
 * 系统参数常量
 *
 * @author travis
 * @date 2019-04-04 00:46
 */

public class BusinessConstant {

    /**
     * 全局配置
     */
    public static final Integer FLAG_NO = 0;
    public static final Integer FLAG_YES = 1;

    /**
     * 管理控台菜单
     */
    public static final Integer MENU_TYPE_MENU = 0;
    public static final Integer MENU_TYPE_BUTTON = 1;

    /**
     * 管理控台用户
     */
    public static final Long ADMIN_USER_ID_PLATFORM = 2L;

    /**
     * 角色
     */
    public static final Long ADMIN_ROLE_ID_ADMIN = 1L;
    public static final Long ADMIN_ROLE_ID_PLATFORM = 2L;
    public static final Long ADMIN_ROLE_ID_OP = 3L;
    public static final Long ADMIN_ROLE_ID_AGENT = 4L;
    public static final Long ADMIN_ROLE_ID_DEPT = 5L;
    public static final Long ADMIN_ROLE_ID_AE = 6L;

    /**
     * 登录方式
     */
    public static final Integer USER_LOGIN_RECORD_TYPE_PWD = 0;
    public static final Integer USER_LOGIN_RECORD_TYPE_AUTHCODE = 1;
    public static final Integer USER_LOGIN_RECORD_TYPE_TOKEN = 2;

    /**
     * 系统公告
     */
    public static final Integer NOTICE_RELEASE_TYPE_CLIENT = 0;
    public static final Integer NOTICE_RELEASE_TYPE_ADMIN = 1;


    /**
     * 余额明细
     */
    public static final Integer BALANCE_RECORD_SOURCE_TYPE_SYSTEM = 0;
    public static final Integer BALANCE_RECORD_SOURCE_TYPE_FREEZE = 1;
    public static final Integer BALANCE_RECORD_SOURCE_TYPE_UNFREEZE = 2;
    public static final Integer BALANCE_RECORD_SOURCE_TYPE_RECHARGE = 10;
    public static final Integer BALANCE_RECORD_SOURCE_TYPE_WITHDRAW = 20;
    public static final Integer BALANCE_RECORD_SOURCE_TYPE_WITHDRAW_CHARGE = 21;
    public static final Integer BALANCE_RECORD_SOURCE_TYPE_TRADE_INCOME = 30;
    public static final Integer BALANCE_RECORD_SOURCE_TYPE_TRADE_CHARGE = 31;

    /**
     * 充值订单
     */
    public static final Integer RECHARGE_ORDER_STATUS_UNPAID = 0;
    public static final Integer RECHARGE_ORDER_STATUS_PAID = 1;
    public static final Integer RECHARGE_ORDER_STATUS_CLOSED = 2;

    /**
     * 提现申请
     */
    public static final Integer WITHDRAW_ORDER_STATUS_UNCHECK = 0;
    public static final Integer WITHDRAW_ORDER_STATUS_PASS = 1;
    public static final Integer WITHDRAW_ORDER_STATUS_REFUSED = 2;

    /**
     * 支付通道
     */
    public static final String CHANNEL_PAY_TYPE_URL = "url";
    public static final String CHANNEL_PAY_TYPE_FORM = "form";
    public static final String CHANNEL_KEY_MANUAL = "manual";
    public static final Integer CHANNEL_TYPE_ALIPAY = 0;
    public static final Integer CHANNEL_TYPE_WXPAY = 1;
    public static final Integer CHANNEL_TYPE_BANK = 2;
    public static final Integer CHANNEL_TYPE_DIGICCY = 3;
    public static final Integer CHANNEL_TYPE_UNIONPAY_QUICK_PASS = 4;
    public static final Integer CHANNEL_TYPE_ALIPAY_ACCOUNT = 5;


    /**
     * 交易订单
     */
    public static final Integer TRADE_ORDER_TYPE_RISE = 0;
    public static final Integer TRADE_ORDER_TYPE_DOWN = 1;
    public static final Integer TRADE_ORDER_CLOSE_TYPE_MANUAL = 0;
    public static final Integer TRADE_ORDER_CLOSE_TYPE_STOP_PROFIT = 1;
    public static final Integer TRADE_ORDER_CLOSE_TYPE_STOP_LOSS = 2;
    public static final Integer TRADE_ORDER_CLOSE_TYPE_STOP_SYSTEM = 3;

    /**
     * 留言咨询
     */
    public static final Integer QUESTION_STATUS_UNREPLY = 0;
    public static final Integer QUESTION_STATUS_REPLY = 1;
}
