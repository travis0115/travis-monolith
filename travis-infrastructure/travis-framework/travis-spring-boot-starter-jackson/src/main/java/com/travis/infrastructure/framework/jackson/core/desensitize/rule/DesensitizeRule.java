package com.travis.infrastructure.framework.jackson.core.desensitize.rule;


import com.travis.infrastructure.framework.jackson.core.desensitize.enums.DesensitizeStrategy;

public interface DesensitizeRule {
    DesensitizeStrategy getStrategy();

    String apply(String value);

}
