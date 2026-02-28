package com.travis.infrastructure.framework.jackson.core.desensitize.spel;

import org.springframework.expression.EvaluationContext;

public interface EvaluationContextProvider {
    /**
     * 构建 SpEL EvaluationContext
     */
    EvaluationContext getContext();
}
