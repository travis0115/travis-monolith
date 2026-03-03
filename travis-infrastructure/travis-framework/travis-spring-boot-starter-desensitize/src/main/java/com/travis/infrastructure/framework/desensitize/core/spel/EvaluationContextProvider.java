package com.travis.infrastructure.framework.desensitize.core.spel;

import org.springframework.expression.EvaluationContext;

public interface EvaluationContextProvider {
    /**
     * 构建 SpEL EvaluationContext
     */
    EvaluationContext getContext();
}
