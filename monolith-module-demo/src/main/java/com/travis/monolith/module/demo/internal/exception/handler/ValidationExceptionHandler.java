package com.travis.monolith.module.demo.internal.exception.handler;

import com.travis.monolith.module.demo.internal.constant.CommonConstant;
import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import com.travis.monolith.module.demo.internal.model.dto.Response;
import com.travis.monolith.module.demo.internal.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 校验异常处理
 *
 * @author Tibbers
 * @date 2017-07-03 15:14
 */

@Slf4j
@RestControllerAdvice
@Order(3)
@SuppressWarnings("all")
public class ValidationExceptionHandler {
    /**
     * 参数校验失败
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Response constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error(ErrorCodeEnum.VALIDATE_FAILED, ex.getMessage());
    }


    /**
     * 参数绑定异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Response bindExceptionHandler(BindException ex) {
        log.error(ex.getMessage(), ex);
        var errorMsg = new StringBuilder();
        for (FieldError error : ex.getFieldErrors()) {
            errorMsg.append(error.getField()).append(":").append(error.getDefaultMessage()).append(",");
        }
        return ResponseUtils.error(ErrorCodeEnum.VALIDATE_FAILED, errorMsg.substring(0, errorMsg.lastIndexOf(",")));
    }
}
