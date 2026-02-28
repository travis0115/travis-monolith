package com.travis.infrastructure.framework.logging.core.condition;

import ch.qos.logback.core.boolex.ExpressionPropertyCondition;
import cn.hutool.core.util.StrUtil;

public class TravisExpressionPropertyCondition extends ExpressionPropertyCondition {

    private static final String PROPERTY_EQUALS_IGNORE_CASE_FUNCTION_KEY = "propertyEqualsIgnoreCase";

    public TravisExpressionPropertyCondition() {
        biFunctionMap.put(PROPERTY_EQUALS_IGNORE_CASE_FUNCTION_KEY, this::propertyEqualsIgnoreCase);

    }

    public boolean propertyEqualsIgnoreCase(String propertyKey, String value) {
        return StrUtil.equalsIgnoreCase(property(propertyKey), value);
    }

}