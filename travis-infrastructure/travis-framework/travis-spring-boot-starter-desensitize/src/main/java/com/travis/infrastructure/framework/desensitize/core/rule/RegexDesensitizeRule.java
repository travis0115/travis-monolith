package com.travis.infrastructure.framework.desensitize.core.rule;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Pattern;

public record RegexDesensitizeRule(Pattern pattern, String replacer) implements DesensitizeRule {

    public RegexDesensitizeRule(String regex, String replacer) {
        this(Pattern.compile(regex), replacer);
    }

    @Override
    public String apply(String value) {
        if (value == null) return null;
        if (StrUtil.isBlank(value)) return "";
        return pattern.matcher(value).replaceAll(replacer);
    }
}