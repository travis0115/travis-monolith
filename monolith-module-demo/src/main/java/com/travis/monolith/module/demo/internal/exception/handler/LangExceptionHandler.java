package com.travis.monolith.module.demo.internal.exception.handler;

import com.travis.monolith.module.demo.internal.constant.CommonConstant;
import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import com.travis.monolith.module.demo.internal.model.dto.Response;
import com.travis.monolith.module.demo.internal.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * lang异常处理
 *
 * @author Tibbers
 * @date 2017-07-03 15:14
 */

@Slf4j
@RestControllerAdvice
@Order(5)
@SuppressWarnings("all")
public class LangExceptionHandler {

    /**
     * 通用异常处理
     */
    @ExceptionHandler(Exception.class)
    public Response exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error();
    }

    /**
     * 算数异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    public Response arithmeticExceptionHandler(HttpServletRequest request, ArithmeticException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error(ErrorCodeEnum.ARITHMETIC_EXCEPTION);
    }


    /**
     * 中断异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(InterruptedException.class)
    public Response interruptedExceptionHandler(HttpServletRequest request, InterruptedException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error(ErrorCodeEnum.INTERRUPTED);
    }

    /**
     * 数字格式错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(NumberFormatException.class)
    public Response numberFormatExceptionHandler(HttpServletRequest request, NumberFormatException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error(ErrorCodeEnum.VALIDATE_NUMBERFORMAT_EXCEPTION);
    }
}
