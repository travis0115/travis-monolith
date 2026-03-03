package com.travis.infrastructure.framework.desensitize.config;

import com.travis.infrastructure.framework.desensitize.core.spel.DefaultEvaluationContextProvider;
import com.travis.infrastructure.framework.desensitize.core.spel.EvaluationContextProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class TravisDesensitizeAutoConfiguration {

    /**
     * 默认的SpEL表达式解析器提供者
     * 用于脱敏注解 disable 表达式
     */
    @Bean
    @ConditionalOnMissingBean
    public EvaluationContextProvider evaluationContextProvider() {
        return new DefaultEvaluationContextProvider();
    }
}