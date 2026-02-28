package com.travis.infrastructure.framework.jackson.core.desensitize.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.travis.infrastructure.framework.jackson.core.desensitize.enums.DesensitizeStrategy;
import com.travis.infrastructure.framework.jackson.core.desensitize.serializer.StringDesensitizeSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 顶级脱敏注解，自定义注解需要使用此注解
 *
 * @author travis
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = StringDesensitizeSerializer.class)   //指定序列化器
public @interface DesensitizeBy {

    /**
     * 脱敏策略
     */
    DesensitizeStrategy value();

}
