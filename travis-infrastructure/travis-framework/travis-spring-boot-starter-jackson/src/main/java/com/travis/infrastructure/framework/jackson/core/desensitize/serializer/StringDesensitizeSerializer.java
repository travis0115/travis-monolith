package com.travis.infrastructure.framework.jackson.core.desensitize.serializer;


import cn.hutool.core.util.StrUtil;
import com.travis.infrastructure.framework.desensitize.core.annotation.RegexDesensitize;
import com.travis.infrastructure.framework.desensitize.core.annotation.SliderDesensitize;
import com.travis.infrastructure.framework.desensitize.core.resolver.DesensitizeResolver;
import com.travis.infrastructure.framework.desensitize.core.rule.DesensitizeRule;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.ser.std.StdSerializer;

import java.lang.annotation.Annotation;

/**
 * 脱敏序列化器
 * <p>
 *
 * @author travis
 */
@SuppressWarnings("rawtypes")
public class StringDesensitizeSerializer extends StdSerializer<String> {

    private final DesensitizeRule rule;

    public StringDesensitizeSerializer() {
        super(String.class);
        this.rule = null;
    }

    public StringDesensitizeSerializer(DesensitizeRule rule) {
        super(String.class);
        this.rule = rule;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializationContext provider) {
        if (StrUtil.isBlank(value)) {
            gen.writeString("");
            return;
        }
        if (rule == null) {
            gen.writeString(value);
            return;
        }
        gen.writeString(rule.apply(value));
    }

    @Override
    public ValueSerializer<?> createContextual(SerializationContext ctxt, BeanProperty property) {
        if (property == null || property.getMember() == null) {
            return this;
        }
        var member = property.getMember();
        Annotation strategyAnnotation = null;
        for (var annotation : member.annotations().toList()) {
            if (annotation.annotationType() == SliderDesensitize.class ||
                    annotation.annotationType() == RegexDesensitize.class) {
                strategyAnnotation = annotation;
                continue;
            }
            var rule = DesensitizeResolver.resolveRule(annotation);
            if (rule != null) {
                return new StringDesensitizeSerializer(rule);
            }
        }
        if (strategyAnnotation != null) {
            var rule = DesensitizeResolver.resolveRule(strategyAnnotation);
            if (rule != null) {
                return new StringDesensitizeSerializer(rule);
            }
        }
        return this;
    }


}
