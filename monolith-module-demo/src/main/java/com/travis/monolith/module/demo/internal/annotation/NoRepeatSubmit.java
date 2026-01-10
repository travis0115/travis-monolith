package com.travis.monolith.module.demo.internal.annotation;

import java.lang.annotation.*;

/**
 * @author travis
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NoRepeatSubmit {

    /**
     * 请求锁定时间
     * 秒
     *
     * @return
     */
    long time() default 5L;

}
