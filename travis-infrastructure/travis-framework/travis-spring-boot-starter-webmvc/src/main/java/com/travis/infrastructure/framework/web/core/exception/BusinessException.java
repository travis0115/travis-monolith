package com.travis.infrastructure.framework.web.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public final class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final IErrorCode errorCode;

    /**
     * 错误参数
     */
    private final Object[] args;

}
