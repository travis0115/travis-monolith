package com.travis.monolith.module.demo.internal.enums;


import lombok.Getter;
import lombok.Setter;

/**
 * @author travis
 */
public enum ErrorCodeEnum {
    /**
     * 通用状态码
     */
    SUCCESS("200", "system.success"),
    ERROR("500", "system.error"),
    UNAUTHORIZED("401", "system.unauthorized"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("402", "system.http_request_method_not_supported"),
    ACCESS_DENIED("403", "system.access_denied"),
    RESOURCE_NOT_FOUND("404", "system.resource_not_found"),
    IO_EXCEPTION("SYS_001", "system.io_exception"),
    INTERRUPTED("SYS_002", "system.interrupted"),
    GET_LOCK_FAILED("SYS_003", "system.get_lock_failed"),
    REPEAT_SUBMIT("SYS_004", "system.repeat_submit"),
    GET_PROPERTY_FAILED("SYS_005", "system.get_property_failed"),
    HTTP_REQUEST_EXCEPTION("SYS_006", "system.http_request_exception"),
    ARITHMETIC_EXCEPTION("SYS_007", "system.arithmetic_exception"),

    /**
     * 数据库
     */
    DATABASE_SELECT_NOT_FOUND("DS_404", "database.select_not_found"),
    DATABASE_INSERT_FAILED("DS_001", "database.insert_failed"),
    DATABASE_UPDATE_FAILED("DS_002", "database.update_failed"),
    DATABASE_DELETE_FAILED("DS_003", "database.delete_failed"),
    DATABASE_OPERATION_FAILED("DS_004", "database.operation_failed"),

    /**
     * 缓存
     */
    CACHE_GET_FAILED("CACHE_001", "cache.get_failed"),
    CACHE_SET_FAILED("CACHE_002", "cache.set_failed"),
    CACHE_DELETE_FAILED("CACHE_003", "cache.delete_failed"),
    CACHE_SET_EXPIRE_FAILED("CACHE_004", "cache.set_expire_failed"),
    CACHE_OPERATION_FAILED("CACHE_005", "cache.operation_failed"),

    /**
     * 参数校验
     */
    VALIDATE_FAILED("VA_001", "validate.validate_failed"),
    VALIDATE_NUMBERFORMAT_EXCEPTION("VA_002", "validate.numberformat_exception"),
    VALIDATE_METHOD_ARGUMENT_TYPE_MISMATCH("VA_003", "validate.method_argument_type_mismatch"),
    VALIDATE_REQUEST_PARAMS_EMPTY("VA_004", "validate.request_params_empty"),

    /**
     * IP
     */
    IP_CONVERT_TO_ADDRESS_FAILED("IP_001", "ip.convert_to_address_failed"),

    /**
     * 短信
     */
    AUTHCODE_SEND_FAILED("AU_001", "authcode.send_failed"),
    AUTHCODE_INVALID("AU_002", "authcode.invalid"),
    AUTHCODE_VALIDATE_FAILED("AU_003", "authcode.validate_failed"),
    AUTHCODE_MOBILE_LIMIT("AU_004", "authcode.mobile_limit"),
    AUTHCODE_IP_LIMIT("AU_005", "authcode.ip_limit"),

    /**
     * 图片验证码
     */
    KAPTCHA_CREATE_FAILED("KA_001", "kaptcha.create_failed"),
    KAPTCHA_INVALID("KA_002", "kaptcha.invalid"),
    KAPTCHA_VALIDATE_FAILED("KA_003", "kaptcha.validate_failed"),

    /**
     * 用户
     */
    USER_USERNAME_NOT_FOUND("U_001", "user.username_not_found"),
    USER_BAD_CREDENTIALS("U_002", "user.bad_credentials"),
    USER_AUTHENTICATION_FAILED("U_003", "user.authentication_failed"),
    USER_ACCOUNT_LOCKED("U_004", "user.account_locked"),
    USER_ACCOUNT_EXPIRED("U_005", "user.account_expired"),
    USER_TOKEN_EXPIRED("U_006", "user.token_expired"),
    USER_TOKEN_INVALID("U_007", "user.token_invalid"),
    USER_TOKEN_EMTPY("U_008", "user.token_emtpy"),
    USER_MOBILE_EXISTED("U_009", "user.mobile_existed"),
    USER_USERNAME_EXISTED("U_010", "user.username_existed"),
    USER_VERIFICATION_FAILED("U_011", "user.verification_failed"),
    USER_CERTIFICATION_LIMIT("U_012", "user.not_certification"),
    USER_CERTIFICATION_FAILED("U_013", "user.certification_failed"),
    USER_CERTIFICATION_REPEAT("U_014", "user.certification_repeat"),
    USER_USABLE_BALANCE_LIMIT("U_015", "user.usable_balance_limit"),
    USER_FROZEN_BALANCE_UPDATE_FAILED("U_016", "user.frozen_balance_update_failed"),
    USER_TRADE_CHARGE_RATIO_LIMIT("U_017", "user.trade_charge_ratio_limit"),
    USER_TRADE_INCOME_RATIO_LIMIT("U_018", "user.trade_income_ratio_limit"),
    USER_AE_JOIN_LIMIT("U_019", "ae.join_ae_limit"),

    /**
     * 角色
     */
    ROLE_WRITABLE_LIMIT("RO_001", "role.writable_limit"),

    /**
     * 订单
     */
    ORDER_STATUS_LIMIT("OR_001", "order.status_limit"),

    /**
     * 提现申请
     */
    WITHDRAW_ORDER_AMOUNT_LIMIT("WO_001", "order.withdraw.amount_limit"),
    WITHDRAW_ORDER_TIME_LIMIT("WO_002", "order.withdraw.time_limit"),

    /**
     * 支付通道
     */
    CHANNEL_USABLE_LIMIT("CH_001", "channel.usable_limit"),
    CHANNEL_AMOUNT_LIMIT("CH_002", "channel.amount_limit"),

    /**
     * 产品
     */
    GOODS_TRADING_FLAG_LIMIT("GO_001", "goods.trading_flag_limit"),
    GOODS_CLOSED("GO_002", "goods.closed"),
    GOODS_USABLE_FLAG_LIMIT("GO_003", "goods.usable_flag_limit"),

    /**
     * 持仓
     */
    TRADE_ORDER_SELLOUT("TO_001", "order.trade.sellout"),

    /**
     * 行情
     */
    QUOTATION_NOT_FOUND("QU_001", "quotation.not_found"),

    ;


    @Getter
    private final String code;

    @Getter
    @Setter
    private String msg;

    ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "code='" + code + ", msg='" + msg;
    }
}
