package com.travis.infrastructure.framework.web.core.exception.advice;

import com.travis.infrastructure.framework.web.core.exception.BusinessException;
import com.travis.infrastructure.framework.web.core.exception.ErrorCode;
import com.travis.infrastructure.framework.web.core.model.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 *
 * @author travis
 */
@RestControllerAdvice
@Slf4j
@NoArgsConstructor
public class CommonExceptionHandlerAdvice {

    /**
     * 兜底
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(HttpServletRequest request, Exception ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> handleBusinessException(BusinessException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.error(ex);
    }

}
