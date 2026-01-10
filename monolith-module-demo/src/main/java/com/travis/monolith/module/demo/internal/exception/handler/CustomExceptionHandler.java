package com.travis.monolith.module.demo.internal.exception.handler;

import com.travis.monolith.module.demo.internal.constant.CommonConstant;
import com.travis.monolith.module.demo.internal.exception.BusinessException;
import com.travis.monolith.module.demo.internal.exception.LockFailedException;
import com.travis.monolith.module.demo.internal.exception.NotFoundException;
import com.travis.monolith.module.demo.internal.exception.RepeatSubmitException;
import com.travis.monolith.module.demo.internal.model.dto.Response;
import com.travis.monolith.module.demo.internal.utils.RedisUtils;
import com.travis.monolith.module.demo.internal.utils.ResponseUtils;
import com.travis.monolith.module.demo.internal.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 自定义异常处理
 *
 * @author travis1215
 * @date 2017-07-03 15:14
 */

@Slf4j
@RestControllerAdvice
@Order(1)
@SuppressWarnings("all")
public class CustomExceptionHandler {

    /**
     * 业务异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Response businessExceptionHandler(HttpServletRequest request, BusinessException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        var requestKey = (String) request.getAttribute("requestKey");
        if (StringUtils.isNotBlank(requestKey)) {
            RedisUtils.delete(requestKey);
        }
        return ResponseUtils.error(ex.getErrorCodeEnum());
    }

    /**
     * 资源未找到异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    public Response NotFoundExceptionHandler(HttpServletRequest request, NotFoundException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error(ex.getErrorCodeEnum());
    }

    /**
     * 未能竞争到锁
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(LockFailedException.class)
    public Response LockFailedExceptionHandler(HttpServletRequest request, LockFailedException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error(ex.getErrorCodeEnum(), "请求超时");
    }

    /**
     * 重复提交
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(RepeatSubmitException.class)
    public Response repeatSubmitExceptionHandler(HttpServletRequest request, RepeatSubmitException ex) {
        log.error("请求编号：" + request.getAttribute(CommonConstant.REQUEST_NO) + "\r\n" + ex.getMessage(), ex);
        return ResponseUtils.error(ex.getErrorCodeEnum());
    }
}
