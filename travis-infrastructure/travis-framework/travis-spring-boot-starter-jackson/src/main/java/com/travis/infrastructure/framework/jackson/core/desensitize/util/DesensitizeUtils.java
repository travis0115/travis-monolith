package com.travis.infrastructure.framework.jackson.core.desensitize.util;


import cn.hutool.core.util.StrUtil;
import com.travis.infrastructure.framework.desensitize.core.resolver.DesensitizeResolver;
import com.travis.infrastructure.framework.desensitize.core.rule.DesensitizeRule;
import com.travis.infrastructure.framework.jackson.core.util.JsonUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 脱敏工具类，供非 Jackson 序列化场景（日志、MDC 等）复用脱敏能力
 *
 * @author travis
 */
public class DesensitizeUtils {

    private static final Map<Class<?>, Map<String, DesensitizeRule>> FIELD_RULE_CACHE = new ConcurrentHashMap<>();

    private DesensitizeUtils() {
    }

    /**
     * 将对象序列化为脱敏后的 JSON 字符串。
     * 复用 ObjectMapper 上注册的 StringDesensitizeSerializer。
     */
    public static String toDesensitizedJson(Object obj) {
        if (obj == null) {
            return "{}";
        }
        return JsonUtils.toJsonString(obj);
    }

    /**
     * 对单个字符串值，按给定注解实例进行脱敏
     */
    public static String desensitize(String value, Annotation annotation) {
        if (StrUtil.isBlank(value) || annotation == null) {
            return value;
        }
        var rule = DesensitizeResolver.resolveRule(annotation);
        return rule != null ? rule.apply(value) : value;
    }

    /**
     * 对单个字符串值，按给定脱敏规则进行脱敏
     */
    public static String desensitize(String value, DesensitizeRule rule) {
        if (StrUtil.isBlank(value) || rule == null) {
            return value;
        }
        return rule.apply(value);
    }

    /**
     * 解析注解类的默认脱敏规则。
     * 例如 resolveDefaultRule(MobileDesensitize.class) 会返回 prefix=3, suffix=4, mask='*' 的 SliderRule。
     * 用于 MDC 等场景，将注解类的默认行为应用于纯字符串。
     */
    public static <A extends Annotation> DesensitizeRule resolveDefaultRule(Class<A> annotationType) {
        A proxy = (A) Proxy.newProxyInstance(
                annotationType.getClassLoader(),
                new Class[]{annotationType},
                (p, method, args) -> switch (method.getName()) {
                    case "annotationType" -> annotationType;
                    case "hashCode" -> System.identityHashCode(p);
                    case "equals" -> p == args[0];
                    case "toString" -> "@" + annotationType.getSimpleName() + "(defaults)";
                    default -> method.getDefaultValue();
                }
        );
        return DesensitizeResolver.resolveRule(proxy);
    }

    /**
     * 扫描指定类（含父类）所有字段上的脱敏注解，返回 fieldName -> DesensitizeRule 映射。
     * 结果会按 Class 级别缓存。
     */
    public static Map<String, DesensitizeRule> resolveFieldRules(Class<?> clazz) {
        return FIELD_RULE_CACHE.computeIfAbsent(clazz, DesensitizeUtils::doResolveFieldRules);
    }

    private static Map<String, DesensitizeRule> doResolveFieldRules(Class<?> clazz) {
        var rules = new HashMap<String, DesensitizeRule>();
        var current = clazz;
        while (current != null && current != Object.class) {
            for (var field : current.getDeclaredFields()) {
                if (rules.containsKey(field.getName())) {
                    continue;
                }
                for (var annotation : field.getAnnotations()) {
                    var rule = DesensitizeResolver.resolveRule(annotation);
                    if (rule != null) {
                        rules.put(field.getName(), rule);
                        break;
                    }
                }
            }
            current = current.getSuperclass();
        }
        return rules.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(rules);
    }
}