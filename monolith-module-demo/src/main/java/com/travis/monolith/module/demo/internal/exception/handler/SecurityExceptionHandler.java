package com.travis.monolith.module.demo.internal.exception.handler;

import com.travis.monolith.module.demo.internal.constant.CommonConstant;
import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import com.travis.monolith.module.demo.internal.model.dto.Response;
import com.travis.monolith.module.demo.internal.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Web异常处理
 *
 * @author Tibbers
 * @date 2017-07-03 15:14
 */

@Slf4j
@RestControllerAdvice
@SuppressWarnings("all")
@Order(4)
public class SecurityExceptionHandler {
    /**
     * AuthenticationException
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public Response authenticationExceptionHandler(HttpServletRequest request, AuthenticationException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error(ErrorCodeEnum.UNAUTHORIZED);
    }

    /**
     * 权限不足
     *
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Response accessDeniedExceptionHandler() {
        return ResponseUtils.error(ErrorCodeEnum.ACCESS_DENIED);
    }

}
