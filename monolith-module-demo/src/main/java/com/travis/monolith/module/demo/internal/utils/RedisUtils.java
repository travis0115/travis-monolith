package com.travis.monolith.module.demo.internal.utils;

import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import com.travis.monolith.module.demo.internal.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 基于spring和redis的redisTemplate工具类
 *
 * @author travis
 * @date 2018-10-27 13:39
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class RedisUtils {
    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(毫秒)
     * @return
     */
    public static void setExpire(String key, long time) {
        try {
            redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_SET_EXPIRE_FAILED);
        }
    }


    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(毫秒) 返回0代表为永久有效
     */
    public static Long getExpire(String key) {
        try {
            return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_GET_FAILED);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_GET_FAILED);
        }
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public static void delete(String... key) {
        try {
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    redisTemplate.delete(key[0]);
                } else {
                    redisTemplate.delete(CollectionUtils.arrayToList(key).toString());
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_DELETE_FAILED);
        }
    }

    /**
     * 删除缓存
     * 模糊匹配
     */
    public static void deleteByReg(String reg) {
        try {
            Set<String> keys = redisTemplate.keys(reg);
            if (!CollectionUtils.isEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_DELETE_FAILED);
        }
    }


    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_GET_FAILED);
        }
    }

    /**
     * 写入缓存
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_SET_FAILED);
        }
    }

    /**
     * 写入缓存并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(毫秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public static void set(String key, Object value, long time) {
        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_SET_FAILED);
        }
    }

    /**
     * 写入缓存，如果不存在
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public static boolean setIfAbsent(String key, Object value) {
        try {
            var result = redisTemplate.opsForValue().setIfAbsent(key, value);
            return result != null && result == true;
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_SET_FAILED);
        }
    }

    /**
     * 写入缓存，如果不存在
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public static boolean setIfAbsent(String key, Object value, long time) {
        try {
            var result = redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
            return result != null && result == true;
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_SET_FAILED);
        }
    }


    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public static void increment(String key, long delta) {
        try {
            redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_SET_FAILED);
        }
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几
     * @return
     */
    public static void decrement(String key, long delta) {
        try {
            redisTemplate.opsForValue().decrement(key, delta);
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.CACHE_SET_FAILED);
        }
    }

}
