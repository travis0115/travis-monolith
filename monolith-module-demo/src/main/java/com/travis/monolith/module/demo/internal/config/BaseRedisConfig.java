package com.travis.monolith.module.demo.internal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

/**
 * Redis基础配置
 * Created by macro on 2020/6/19.
 */
@Configuration
public class BaseRedisConfig {

    /**
     * 配置RedisTemplate
     * 设置键的序列化方式为StringRedisSerializer
     * 设置值的序列化方式为GenericJackson2JsonRedisSerializer
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建RedisTemplate对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 创建JSON序列化器
        GenericJacksonJsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJacksonJsonRedisSerializer(objectMapper());
        // 设置键序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置值序列化
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        // 设置哈希键序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置哈希值序列化
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        // 初始化RedisTemplate
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 配置RedisCacheManager
     * 用于Spring Cache支持
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 缓存配置
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 设置键的序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置值的序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer()))
                // 设置缓存过期时间
                .entryTtl(Duration.ofMinutes(30))
                // 禁用缓存空值
                .disableCachingNullValues();

        // 创建RedisCacheManager
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(cacheConfiguration)
                .build();
    }

    /**
     * 创建ObjectMapper实例
     */
    private tools.jackson.databind.ObjectMapper objectMapper() {
        tools.jackson.databind.ObjectMapper objectMapper = new tools.jackson.databind.ObjectMapper();
        return objectMapper;
    }

    /**
     * 创建JSON序列化器
     */
    private RedisSerializer<Object> genericJackson2JsonRedisSerializer() {
        return new GenericJacksonJsonRedisSerializer(objectMapper());
    }

}
