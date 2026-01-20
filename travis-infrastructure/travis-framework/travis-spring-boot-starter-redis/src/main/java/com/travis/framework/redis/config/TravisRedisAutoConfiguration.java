package com.travis.framework.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import tools.jackson.databind.ObjectMapper;


/**
 * Redis 配置类
 *
 * @author travis
 */
@AutoConfiguration
public class TravisRedisAutoConfiguration {

    private final ObjectMapper objectMapper;

    @Autowired
    public TravisRedisAutoConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    /**
     * 创建 RedisTemplate Bean，使用 JSON 序列化方式
     */
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建 RedisTemplate 对象
        var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);

        var stringRedisSerializer = RedisSerializer.string();
        var jsonRedisSerializer = new GenericJacksonJsonRedisSerializer(objectMapper);

        // 使用 String 序列化 KEY
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        // 使用 JSON 序列化 VALUE
        template.setValueSerializer(jsonRedisSerializer);
        template.setHashValueSerializer(jsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
