package com.travis.infrastructure.framework.jackson.core.desensitize.rule;


import com.travis.infrastructure.framework.jackson.core.desensitize.enums.DesensitizeStrategy;

public record DesensitizeSliderRule(
        int prefix,
        int suffix,
        char mask
) implements DesensitizeRule {
    @Override
    public DesensitizeStrategy getStrategy() {
        return DesensitizeStrategy.SLIDER;
    }

    @Override
    public String apply(String value) {
        if (value == null) return null;
        if (value.length() <= prefix + suffix) {
            return value;
        }
        if (prefix < 0 || suffix < 0) {
            return value;
        }
        var builder = new StringBuilder(value.length());
        builder.append(value, 0, prefix);
        int maskLength = value.length() - prefix - suffix;
        builder.append(String.valueOf(mask).repeat(maskLength));
        builder.append(value.substring(value.length() - suffix));
        return builder.toString();
    }
}
