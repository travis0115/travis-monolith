package com.travis.monolith.module.demo.internal.aspect;

import com.travis.monolith.module.demo.internal.annotation.NoRepeatSubmit;
import com.travis.monolith.module.demo.internal.constant.CommonConstant;
import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import com.travis.monolith.module.demo.internal.exception.RepeatSubmitException;
import com.travis.monolith.module.demo.internal.utils.DateTimeUtils;
import com.travis.monolith.module.demo.internal.utils.RedisUtils;
import com.travis.monolith.module.demo.internal.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author travis
 */
@Component
@Aspect
@Slf4j
@Order(1)
public class NoRepeatSubmitAspect {

    @Pointcut("@annotation(noRepeatSubmit)")
    private void noRepeatSubmitPointcut(NoRepeatSubmit noRepeatSubmit) {

    }

    @Around(value = "noRepeatSubmitPointcut(noRepeatSubmit)", argNames = "joinPoint,noRepeatSubmit")
    public Object around(ProceedingJoinPoint joinPoint, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        var request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        var token = URLDecoder.decode(StringUtils.nullToBlank(request.getHeader(CommonConstant.JWT_HEADER)), StandardCharsets.UTF_8).replaceFirst(CommonConstant.JWT_PRDFIX, "").trim();
        String path = request.getServletPath();
        var args = joinPoint.getArgs();
        var key = "noRepeatSubmit:" + path + token + Arrays.hashCode(args);
        if (RedisUtils.setIfAbsent(key, DateTimeUtils.getFormatTime(), noRepeatSubmit.time())) {
            request.setAttribute("requestKey", key);
            return joinPoint.proceed();
        }
        throw new RepeatSubmitException(ErrorCodeEnum.REPEAT_SUBMIT);
    }
}
