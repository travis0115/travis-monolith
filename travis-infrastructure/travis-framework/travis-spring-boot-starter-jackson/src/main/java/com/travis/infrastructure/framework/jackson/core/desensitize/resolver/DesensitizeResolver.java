package com.travis.infrastructure.framework.jackson.core.desensitize.resolver;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.travis.infrastructure.framework.jackson.core.desensitize.annotation.regex.RegexDesensitize;
import com.travis.infrastructure.framework.jackson.core.desensitize.annotation.slider.SliderDesensitize;
import com.travis.infrastructure.framework.jackson.core.desensitize.cache.AnnotationAttributeSnapshotBuilder;
import com.travis.infrastructure.framework.jackson.core.desensitize.cache.DesensitizeMethodHandleCache;
import com.travis.infrastructure.framework.jackson.core.desensitize.cache.DesensitizeRuleCacheKey;
import com.travis.infrastructure.framework.jackson.core.desensitize.rule.DesensitizeRegexRule;
import com.travis.infrastructure.framework.jackson.core.desensitize.rule.DesensitizeRule;
import com.travis.infrastructure.framework.jackson.core.desensitize.rule.DesensitizeSliderRule;
import com.travis.infrastructure.framework.jackson.core.desensitize.spel.EvaluationContextProvider;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DesensitizeResolver {

    /**
     * SpEL 解析器
     */
    private static final ExpressionParser PARSER = new SpelExpressionParser();


    /**
     * 规则缓存（支持字段参数覆盖）
     */
    private static final Map<DesensitizeRuleCacheKey, DesensitizeRule> RULE_CACHE = new ConcurrentHashMap<>();

    /**
     * SpEL 缓存
     */
    private static final Map<String, Expression> SPEL_CACHE = new ConcurrentHashMap<>();

    /**
     * SpEL 上下文
     */
    private static class ProviderHolder {
        private static final EvaluationContextProvider INSTANCE =
                SpringUtil.getBean(EvaluationContextProvider.class);
    }

    private DesensitizeResolver() {
    }

    /**
     * 解析规则入口
     */
    public static DesensitizeRule resolveRule(Annotation annotation) {
        if (annotation == null) {
            return null;
        }

        var key = new DesensitizeRuleCacheKey(annotation.annotationType(),
                AnnotationAttributeSnapshotBuilder.buildSnapshot(annotation));
        return RULE_CACHE.computeIfAbsent(key,
                _ -> resolveRuleDeep(annotation, Collections.newSetFromMap(new IdentityHashMap<>())
                ));
    }

    /**
     * 深度递归解析注解链，支持：
     * 1. 直接策略注解
     * 2. 业务注解使用策略注解作为元注解
     * 3. 多层元注解
     */
    private static DesensitizeRule resolveRuleDeep(Annotation annotation, Set<Class<?>> visited) {
        var type = annotation.annotationType();
        // 防止循环递归
        if (!visited.add(type)) {
            return null;
        }

        /*
         * 检查 disable
         */
        if (isDisabled(annotation)) {
            return null;
        }

        /*
         * 字段注解 RegexDesensitize
         */
        if (type == RegexDesensitize.class) {
            return buildRegexRule(annotation, (RegexDesensitize) annotation);
        }

        /*
         * 字段注解 SliderDesensitize
         */
        if (type == SliderDesensitize.class) {
            return buildSliderRule(annotation, (SliderDesensitize) annotation);
        }

        /*
         * 元注解 RegexDesensitize
         */
        var regexMeta = type.getAnnotation(RegexDesensitize.class);
        if (regexMeta != null) {
            return buildRegexRule(annotation, regexMeta);
        }

        /*
         * 元注解 SliderDesensitize
         */
        var sliderMeta = type.getAnnotation(SliderDesensitize.class);
        if (sliderMeta != null) {
            return buildSliderRule(annotation, sliderMeta);
        }

        /*
         * Recursive Search Meta Annotation
         */
        for (Annotation meta : type.getAnnotations()) {
            var rule = resolveRuleDeep(meta, visited);
            if (rule != null) {
                return rule;
            }
        }
        return null;
    }


    private static DesensitizeRegexRule buildRegexRule(Annotation sourceAnnotation, RegexDesensitize meta) {
        var regex = resolveAnnotationField(sourceAnnotation, "regex", meta.regex(), String.class);
        var replacer = resolveAnnotationField(sourceAnnotation, "replacer", meta.replacer(), String.class);
        return new DesensitizeRegexRule(regex, replacer);
    }


    private static DesensitizeSliderRule buildSliderRule(Annotation sourceAnnotation, SliderDesensitize meta) {
        var prefix = resolveAnnotationField(sourceAnnotation, "prefix", meta.prefix(), int.class);
        var suffix = resolveAnnotationField(sourceAnnotation, "suffix", meta.suffix(), int.class);
        var mask = resolveAnnotationField(sourceAnnotation, "mask", meta.mask(), char.class);
        return new DesensitizeSliderRule(prefix, suffix, mask);
    }

    private static <T> T resolveAnnotationField(Annotation annotation, String fieldName, T defaultValue,
                                                Class<T> type) {
        try {
            var handle = DesensitizeMethodHandleCache.getHandle(annotation.annotationType(), fieldName);
            if (handle == null) {
                return defaultValue;
            }
            var value = handle.invoke(annotation);
            if (value == null) {
                return defaultValue;
            }

            if (type == String.class) {
                if (StrUtil.isNotBlank((String) value)) {
                    return (T) value;
                }
                return defaultValue;
            }

            if (type == Integer.class || type == int.class) {
                int v = ((Number) value).intValue();
                if (v != -1) {
                    return (T) Integer.valueOf(v);
                }
                return defaultValue;
            }

            if (type == Character.class || type == char.class) {
                char v = (Character) value;
                if (v != 0) {
                    return (T) Character.valueOf(v);
                }
                return defaultValue;
            }

            return (T) value;

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Spel disable 统一解析
     */
    private static boolean isDisabled(Annotation annotation) {
        var handle = DesensitizeMethodHandleCache.getHandle(annotation.annotationType(), "disable");
        if (handle == null) {
            return false;
        }
        try {
            var expr = (String) handle.invoke(annotation);
            if (StrUtil.isBlank(expr)) {
                return false;
            }
            var expression = SPEL_CACHE.computeIfAbsent(expr, PARSER::parseExpression);
            var context = ProviderHolder.INSTANCE.getContext();
            var result = expression.getValue(context, Boolean.class);
            return Boolean.TRUE.equals(result);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


}
