package com.travis.monolith.module.demo.internal.exception.handler;

import com.travis.monolith.module.demo.internal.constant.CommonConstant;
import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import com.travis.monolith.module.demo.internal.model.dto.Response;
import com.travis.monolith.module.demo.internal.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * Web异常处理
 *
 * @author Tibbers
 * @date 2017-07-03 15:14
 */

@Slf4j
@RestControllerAdvice
@SuppressWarnings("all")
@Order(2)
public class WebExceptionHandler {
    /**
     * 404
     *
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Response noHandlerFoundExceptionHandler() {
        return ResponseUtils.error(ErrorCodeEnum.RESOURCE_NOT_FOUND);
    }


    /**
     * 请求方法不支持
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpServletResponse response, HttpRequestMethodNotSupportedException ex) {
        return ResponseUtils.error(ErrorCodeEnum.HTTP_REQUEST_METHOD_NOT_SUPPORTED, "http request method not support：" + request.getMethod());
    }

    /**
     * 参数类型错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Response methodArgumentTypeMismatchExceptionHandler(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error(ErrorCodeEnum.VALIDATE_METHOD_ARGUMENT_TYPE_MISMATCH, "argument type mismatch：" + ex.getName());
    }

    /**
     * 参数校验失败
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error(ErrorCodeEnum.VALIDATE_METHOD_ARGUMENT_TYPE_MISMATCH, ex.getMessage());
    }

    /**
     * 参数丢失异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response missingServletRequestParameterExceptionHandler(HttpServletRequest request, MissingServletRequestParameterException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error(ErrorCodeEnum.VALIDATE_FAILED, ex.getParameterName() + "is required");
    }

}
