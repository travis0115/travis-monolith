package com.travis.monolith.module.demo.internal.annotation;

import java.lang.annotation.*;

/**
 * @author travis
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisLockConfig {
    /**
     * redis锁的命名空间
     *
     * @return
     */
    String lockName() default "";

}
