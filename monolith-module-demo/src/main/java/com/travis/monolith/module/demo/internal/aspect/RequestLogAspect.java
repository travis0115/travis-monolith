package com.travis.monolith.module.demo.internal.aspect;

import com.alibaba.fastjson2.JSONObject;
import com.travis.monolith.module.demo.internal.constant.CommonConstant;
import com.travis.monolith.module.demo.internal.utils.RedisUtils;
import com.travis.monolith.module.demo.internal.utils.StringUtils;
import com.travis.monolith.module.demo.internal.utils.UUIDUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author travis
 * @date 2019-04-03 13:09
 */
@Component
@Aspect
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class RequestLogAspect {
    private final HttpServletRequest request;

    @Autowired
    public RequestLogAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Pointcut("execution(* com.travis.monolith.*.controller..*.*(..))")
    public void requestLogPointcut() {
    }

    @Before("requestLogPointcut()")
    public void before(JoinPoint joinPoint) {
        synchronized (this) {
            var requestNo = UUIDUtils.get();
            request.setAttribute(CommonConstant.REQUEST_NO, requestNo);
            log.info("===============请求begin===============");
            log.info("请求编号：{}", requestNo);
            log.info("请求地址:{}", request.getRequestURL());
            log.info("请求方式:{}", request.getMethod());
//            log.info("客户端IP:{}", IpUtils.getIp());
            log.info("请求类方法:{}", joinPoint.getSignature());
            log.info("请求类方法参数:{}", Arrays.toString(joinPoint.getArgs()));
            log.info("===============请求end===============");
            System.out.println();
        }
    }

    @AfterReturning(value = "requestLogPointcut()", returning = "response")
    public void afterReturning(Object response) {
        synchronized (this) {
            var requestKey = (String) request.getAttribute("requestKey");
            if (StringUtils.isNotBlank(requestKey)) {
                RedisUtils.delete(requestKey);
            }
            log.info("===============返回begin===============");
            log.info("请求编号：{}", request.getAttribute(CommonConstant.REQUEST_NO));
            log.info("返回结果：{}", JSONObject.toJSONString(response));
            log.info("===============返回end===============");
            System.out.println();
        }
    }
}
