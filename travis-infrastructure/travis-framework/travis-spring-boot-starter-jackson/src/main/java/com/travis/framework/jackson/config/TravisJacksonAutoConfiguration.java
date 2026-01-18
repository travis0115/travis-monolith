package com.travis.framework.jackson.config;

import cn.hutool.core.text.CharSequenceUtil;
import com.travis.framework.jackson.core.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.module.SimpleModule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author travis
 */
@AutoConfiguration(after = JacksonProperties.class)
@EnableConfigurationProperties({JacksonProperties.class})
public class TravisJacksonAutoConfiguration {

    /**
     * 配置Jackson JSON映射器的自定义构建器
     */
    @Bean
    JsonMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> builder
                .enable(SerializationFeature.INDENT_OUTPUT) // 格式化输出
                .disable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)    //成功解析值后不验证是否存在额外内容(缓存内容可信)
                // 添加默认类型到属性中，LaissezFaireSubTypeValidator 是全局放行的校验器，相当于关闭了校验，仅在序列化对象可信时使用，否则请使用白名单校验器 BasicPolymorphicTypeValidator
                .activateDefaultTypingAsProperty(new LaissezFaireSubTypeValidator(), DefaultTyping.NON_FINAL_AND_ENUMS, "@class")
                .build();
    }

    /**
     * 创建自定义Java8时间序列化解析模块
     * 该模块用于处理LocalDateTime类型的JSON序列化和反序列化，支持通过全局配置的date-format进行格式化
     */
    @Bean
    public JacksonModule javaTimeModule(JacksonProperties jacksonProperties) {
        var javaTimeModule = new SimpleModule();
        if (CharSequenceUtil.isNotBlank(jacksonProperties.getDateFormat())) {
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(jacksonProperties.getDateFormat())))
                    .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(jacksonProperties.getDateFormat())));
        }
        return javaTimeModule;
    }

}
