package com.travis.infrastructure.framework.jackson.core.desensitize.serializer;

import com.travis.infrastructure.framework.desensitize.core.spi.DesensitizeObjectSerializer;
import com.travis.infrastructure.framework.jackson.core.util.JsonUtils;

/**
 * 基于 Jackson ObjectMapper 的脱敏序列化实现。
 * ObjectMapper 已注册 DesensitizeJacksonModule，序列化时自动触发脱敏。
 */
public class JacksonDesensitizeObjectSerializer implements DesensitizeObjectSerializer {

    @Override
    public String serialize(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof String s) return s;
        return JsonUtils.toJsonString(obj);
    }
}