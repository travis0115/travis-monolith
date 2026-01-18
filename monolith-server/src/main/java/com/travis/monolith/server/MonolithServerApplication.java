package com.travis.monolith.server;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 应用启动入口
 *
 * @author travis
 */

@EnableCaching
@SpringBootApplication
public class MonolithServerApplication {
    private static RedisTemplate<String, Object> redisTemplate;
    private static RedissonClient redissonClient;


    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        MonolithServerApplication.redisTemplate = redisTemplate;
        MonolithServerApplication.redissonClient = redissonClient;
    }

    static void main(String[] args) {
        SpringApplication.run(MonolithServerApplication.class, args);


        redisTemplate.opsForValue().set("localdatetime", LocalDateTime.now());
        redisTemplate.opsForValue().set("date", new Date());

        redisTemplate.opsForValue().set("test", new Test());

        var test = (Test) redisTemplate.opsForValue().get("test");

        System.out.println(test);
    }
}

