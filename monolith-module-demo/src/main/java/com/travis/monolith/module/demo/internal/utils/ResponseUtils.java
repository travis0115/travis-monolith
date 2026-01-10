package com.travis.monolith.module.demo.internal.utils;

import com.alibaba.fastjson2.JSONObject;
import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import com.travis.monolith.module.demo.internal.model.dto.Response;

/**
 * @author travis
 * @date 2018-06-12 23:46
 */
@SuppressWarnings("rawtypes")
public class ResponseUtils {

    public static Response success() {
        return success(new JSONObject());
    }

    public static Response success(Object data) {
        return Response.builder()
                .code(ErrorCodeEnum.SUCCESS.getCode())
                .msg(I18nUtils.getMessage(ErrorCodeEnum.SUCCESS.getMsg()))
                .data(data)
                .build();
    }

    public static Response error() {
        return error(ErrorCodeEnum.ERROR);
    }

    public static Response error(ErrorCodeEnum errorCodeEnum) {
        return Response.builder()
                .code(errorCodeEnum.getCode())
                .msg(I18nUtils.getMessage(errorCodeEnum.getMsg()))
                .data(new JSONObject())
                .build();
    }

    public static Response error(ErrorCodeEnum errorCodeEnum, String msg) {
        errorCodeEnum.setMsg(msg);
        return error(errorCodeEnum);
    }
}
