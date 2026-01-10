package com.travis.monolith.module.demo.internal.annotation;

import java.lang.annotation.*;

/**
 * @author travis
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisLock {
    /**
     * redis锁的命名空间
     *
     * @return
     */
    String lockName() default "";

    /**
     * redis锁的key值
     * spEL表达式
     *
     * @return
     */
    String key() default "";

    /**
     * 超时时间
     *
     * @return
     */
    long time() default 3L;

}
