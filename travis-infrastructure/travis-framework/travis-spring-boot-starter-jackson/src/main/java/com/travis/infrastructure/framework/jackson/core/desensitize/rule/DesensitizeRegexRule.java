package com.travis.infrastructure.framework.jackson.core.desensitize.rule;

import cn.hutool.core.util.StrUtil;
import com.travis.infrastructure.framework.jackson.core.desensitize.enums.DesensitizeStrategy;

import java.util.regex.Pattern;

public record DesensitizeRegexRule(Pattern pattern, String replacer) implements DesensitizeRule {

    public DesensitizeRegexRule(String regex, String replacer) {
        this(Pattern.compile(regex), replacer);
    }

    @Override
    public DesensitizeStrategy getStrategy() {
        return DesensitizeStrategy.REGEX;
    }

    @Override
    public String apply(String value) {
        if (value == null) return null;

        if (StrUtil.isBlank(value)) {
            return "";
        }
        return pattern.matcher(value).replaceAll(replacer);
    }
}
