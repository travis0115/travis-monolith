package com.travis.monolith.module.demo.internal.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Objects;


/**
 * @author travis
 * @date 2019-03-28 01:52
 */
@Configuration
@SuppressWarnings("all")
public class CacheConfig {
    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> template) {
        var defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                //设置value 为自动转Json的Object
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getValueSerializer()))
                //设置默认缓存时间
                .entryTtl(Duration.ofDays(1))
                //不缓存null
                .disableCachingNullValues()
                //自定义缓存前缀名
                .computePrefixWith(cacheKeyPrefix());
        return RedisCacheManager.RedisCacheManagerBuilder
                //Redis 连接工厂
                .fromConnectionFactory(Objects.requireNonNull(template.getConnectionFactory()))
                //缓存配置
                .cacheDefaults(defaultCacheConfiguration)
                //事务感知
                .transactionAware()
                .build();
    }

    @Bean
    public CacheKeyPrefix cacheKeyPrefix() {
        return new CacheKeyPrefix() {
            @Override
            public String compute(String cacheName) {
                return cacheName + ":";
            }
        };
    }

}
