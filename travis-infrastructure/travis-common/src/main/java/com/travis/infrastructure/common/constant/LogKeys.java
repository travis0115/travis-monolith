package com.travis.infrastructure.common.constant;

/**
 * 日志类型枚举
 */
public final class LogKeys {

    /**
     * 日志类型
     * 参考 {@link com.travis.infrastructure.common.enums.LogType}
     */
    public static final String LOG_TYPE = "log_type";

    /**
     * 接口路径
     */
    public static final String API_URL = "api_url";

    /**
     * 接口请求参数
     */
    public static final String REQUEST_PARAMS = "request_params";

    /**
     * 接口请求Body
     */
    public static final String REQUEST_BODY = "request_body";

    /**
     * 接口请求结果
     */
    public static final String API_RESULT = "api_result";

    /**
     * 接口耗时
     */
    public static final String API_COST = "api_cost";

    /**
     * 请求方式
     */
    public static final String HTTP_METHOD = "http_method";

    /**
     * 客户端IP
     */
    public static final String CLIENT_IP = "client_ip";

    /**
     * 客户端信息
     */
    public static final String USER_AGENT = "user_agent";

    /**
     * 客户端类型
     * 参考 {@link com.travis.infrastructure.common.enums.ClientType}
     */
    public static final String CLIENT_TYPE = "client_type";


    private LogKeys() {
    }
}
