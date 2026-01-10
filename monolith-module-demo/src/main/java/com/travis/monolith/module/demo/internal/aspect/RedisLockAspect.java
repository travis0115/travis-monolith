package com.travis.monolith.module.demo.internal.aspect;

import com.travis.monolith.module.demo.internal.annotation.RedisLock;
import com.travis.monolith.module.demo.internal.annotation.RedisLockConfig;
import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import com.travis.monolith.module.demo.internal.exception.BusinessException;
import com.travis.monolith.module.demo.internal.utils.RedisLockUtils;
import com.travis.monolith.module.demo.internal.utils.StringUtils;
import com.travis.monolith.module.demo.internal.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

/**
 * @author travis
 */
@Component
@Aspect
@Slf4j
@SuppressWarnings("all")
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
public class RedisLockAspect {

    @Pointcut("@annotation(redisLock)")
    private void redisLockPointcut(RedisLock redisLock) {
    }

    @Around(value = "redisLockPointcut(redisLock)", argNames = "joinPoint,redisLock")
    public Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        Lock lock = RedisLockUtils.tryLock(getLockKey(joinPoint, redisLock), redisLock.time());
        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } finally {
            lock.unlock();
        }
        return proceed;

    }

    public String getLockKey(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        var lockName = redisLock.lockName();
        if (StringUtils.isBlank(lockName)) {
            var redisLockConfig = joinPoint.getTarget().getClass().getAnnotation(RedisLockConfig.class);
            if (redisLockConfig != null) {
                lockName = redisLockConfig.lockName();
            }
        }
        var key = redisLock.key();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        var method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        var discoverer = new DefaultParameterNameDiscoverer();
        var parameterNames = discoverer.getParameterNames(method);
        var args = joinPoint.getArgs();
        if (parameterNames != null) {
            var parser = new SpelExpressionParser();
            var ctx = new StandardEvaluationContext();
            int len = Math.min(args.length, parameterNames.length);
            for (int i = 0; i < len; i++) {
                ctx.setVariable(parameterNames[i], args[i]);
            }
            Object fieldValue = null;
            try {
                fieldValue = parser.parseExpression(key).getValue(ctx);
                return lockName + ":" + fieldValue;
            } catch (Exception e) {
                throw new BusinessException(e, ErrorCodeEnum.GET_PROPERTY_FAILED);
            }
        }
        return UUIDUtils.get();
    }

}
