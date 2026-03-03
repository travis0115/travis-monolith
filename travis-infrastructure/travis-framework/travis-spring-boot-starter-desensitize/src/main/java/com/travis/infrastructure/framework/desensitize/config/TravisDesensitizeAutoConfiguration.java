package com.travis.infrastructure.framework.desensitize.config;

import com.travis.infrastructure.framework.desensitize.core.jackson.modules.DesensitizeJacksonModule;
import com.travis.infrastructure.framework.desensitize.core.jackson.serializer.JacksonDesensitizeObjectSerializer;
import com.travis.infrastructure.framework.desensitize.core.spel.DefaultEvaluationContextProvider;
import com.travis.infrastructure.framework.desensitize.core.spel.EvaluationContextProvider;
import com.travis.infrastructure.framework.desensitize.core.spi.DesensitizeObjectSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.ObjectMapper;

@AutoConfiguration
public class TravisDesensitizeAutoConfiguration {

    /**
     * 注册脱敏 Jackson Module，自动发现 @DesensitizeBy 注解并绑定序列化器
     */
    @Bean
    public JacksonModule desensitizeJacksonModule() {
        return new DesensitizeJacksonModule();
    }

    /**
     * 注册 SPI 实现脱敏工具类，驱动 DesensitizeUtils.toDesensitizedJson() 和 ofDesensitizedJson()
     * DesensitizeUtils 从容器按需获取本 Bean，无需再调用 setObjectSerializer。
     */
    @Bean
    @ConditionalOnMissingBean
    public DesensitizeObjectSerializer desensitizeObjectSerializer(ObjectMapper objectMapper) {
        return new JacksonDesensitizeObjectSerializer(objectMapper);
    }

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