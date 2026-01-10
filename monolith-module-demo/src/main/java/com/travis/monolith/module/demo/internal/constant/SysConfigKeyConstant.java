package com.travis.monolith.module.demo.internal.constant;

/**
 * 系统参数键值
 *
 * @author travis
 * @date 2019-04-04 00:46
 */

public class SysConfigKeyConstant {
    /**
     * api url前缀
     */
    public static final String API_PREFIX = "api_prefix";

    /**
     * 静态资源url前缀
     */
    public static final String RESOURCE_PREFIX = "resource_prefix";

    /**
     * websocket url前缀
     */
    public static final String WEBSOCKET_PREFIX = "websocket_prefix";

    /**
     * H5页面url前缀
     */
    public static final String H5_PREFIX = "h5_prefix";

    /**
     * 弹出公告
     */
    public static final String POPUP_NOTICE = "popup_notice";

    /**
     * 相同ip每日发送短信次数
     */
    public static final String AUTHCODE_IP_LIMIT = "authcode_ip_limit";

    /**
     * 相同手机号每日发送短信次数
     */
    public static final String AUTHCODE_MOBILE_LIMIT = "authcode_mobile_limit";

    /**
     * APP下载地址（iOS）
     */
    public static final String IOS_DOWNLOAD_URL = "ios_download_url";

    /**
     * APP下载地址（Android）
     */
    public static final String ANDROID_DOWNLOAD_URL = "android_download_url";

    /**
     * 在线客服网址
     */
    public static final String ONLINE_SERVICE_URL = "online_service_url";

    /**
     * 首页底部横幅
     */
    public static final String HOME_TIP = "home_tip";

    /**
     * 用户提现手续费占比（单位：%）
     */
    public static final String WITHDRAW_CHARGE_RATIO = "withdraw_charge_ratio";

    /**
     * 机构提现手续费占比（单位：%）
     */
    public static final String ADMIN_USER_WITHDRAW_CHARGE_RATIO = "admin_user_withdraw_charge_ratio";

    /**
     * 用户单笔提现最高金额
     */
    public static final String WITHDRAW_MAXIMUM_AMOUNT = "withdraw_maximum_amount";

    /**
     * 用户单笔提现最低金额
     */
    public static final String WITHDRAW_MINIMUM_AMOUNT = "withdraw_minimum_amount";

    /**
     * 代理最大数量
     */
    public static final String AGENT_MAX_COUNT = "agent_max_count";

    /**
     * 机构单笔提现最高金额
     */
    public static final String ADMIN_USER_WITHDRAW_MAXIMUM_AMOUNT = "admin_user_withdraw_maximum_amount";

    /**
     * 机构单笔提现最低金额
     */
    public static final String ADMIN_USER_WITHDRAW_MINIMUM_AMOUNT = "admin_user_withdraw_minimum_amount";

    /**
     * USDT兑系统货币汇率
     */
    public static final String EXCHANGE_RATE_USDT = "exchange_rate_usdt";
}
