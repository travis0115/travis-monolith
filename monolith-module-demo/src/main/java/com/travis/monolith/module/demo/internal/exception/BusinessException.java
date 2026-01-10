package com.travis.monolith.module.demo.internal.exception;

import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author travis
 * @date 2018-05-26 15:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 8902960483635291962L;
    private ErrorCodeEnum errorCodeEnum;

    public BusinessException() {
        this(ErrorCodeEnum.ERROR);
    }

    public BusinessException(String msg) {
        this(new Exception(), msg);
    }

    public BusinessException(Throwable cause, String msg) {
        super(msg, cause);
        this.errorCodeEnum = ErrorCodeEnum.ERROR;
        this.errorCodeEnum.setMsg(msg);
    }


    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        this(new Exception(errorCodeEnum.getMsg()), errorCodeEnum);
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(msg);
        this.errorCodeEnum = errorCodeEnum;
        this.errorCodeEnum.setMsg(msg);
    }

    public BusinessException(Throwable cause) {
        this(cause, ErrorCodeEnum.ERROR);
    }

    public BusinessException(Throwable cause, ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg(), cause);
        this.errorCodeEnum = errorCodeEnum;
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }

}
