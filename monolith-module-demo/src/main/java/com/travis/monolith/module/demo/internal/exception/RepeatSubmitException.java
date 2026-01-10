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
public class RepeatSubmitException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ErrorCodeEnum errorCodeEnum;

    public RepeatSubmitException() {
        this(ErrorCodeEnum.ERROR);
    }

    public RepeatSubmitException(String msg) {
        this(new Exception(), msg);
    }

    public RepeatSubmitException(Throwable cause, String msg) {
        super(msg, cause);
        this.errorCodeEnum = ErrorCodeEnum.ERROR;
        this.errorCodeEnum.setMsg(msg);
    }


    public RepeatSubmitException(ErrorCodeEnum errorCodeEnum) {
        this(null, errorCodeEnum);
    }

    public RepeatSubmitException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(msg);
        this.errorCodeEnum = errorCodeEnum;
        this.errorCodeEnum.setMsg(msg);
    }

    public RepeatSubmitException(Throwable cause) {
        this(cause, ErrorCodeEnum.ERROR);
    }

    public RepeatSubmitException(Throwable cause, ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg(), cause);
        this.errorCodeEnum = errorCodeEnum;
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }

}
