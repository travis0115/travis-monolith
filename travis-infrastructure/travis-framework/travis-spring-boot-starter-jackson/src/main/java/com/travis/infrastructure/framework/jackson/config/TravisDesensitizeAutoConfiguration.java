package com.travis.infrastructure.framework.jackson.config;

import com.travis.infrastructure.framework.jackson.core.desensitize.spel.DefaultEvaluationContextProvider;
import com.travis.infrastructure.framework.jackson.core.desensitize.spel.EvaluationContextProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author travis
 */
@AutoConfiguration
public class TravisDesensitizeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EvaluationContextProvider evaluationContextProvider() {
        return new DefaultEvaluationContextProvider();
    }

}
