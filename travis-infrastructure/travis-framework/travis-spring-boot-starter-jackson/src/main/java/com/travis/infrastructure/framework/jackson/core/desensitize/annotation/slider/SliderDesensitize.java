package com.travis.infrastructure.framework.jackson.core.desensitize.annotation.slider;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.travis.infrastructure.framework.jackson.core.desensitize.enums.DesensitizeStrategy;
import com.travis.infrastructure.framework.jackson.core.desensitize.annotation.DesensitizeBy;

import java.lang.annotation.*;

/**
 * 滑动脱敏注解
 *
 * @author travis
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.RECORD_COMPONENT, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(DesensitizeStrategy.SLIDER)
public @interface SliderDesensitize {

    /**
     * 前缀保留长度
     */
    int prefix() default 0;

    /**
     * 后缀保留长度
     */
    int suffix() default 0;

    /**
     * 替换规则，会将前缀后缀保留后，全部替换成 mask
     * <p>
     * 例如：prefixKeep = 1; suffixKeep = 2; replacer = "*";
     * 原始字符串  123456
     * 脱敏后     1***56
     */
    char mask() default '*';


    /**
     * 是否禁用脱敏
     * <p>
     * 支持 Spring EL 表达式，如果返回 true 则跳过脱敏
     */
    String disable() default "";

}
