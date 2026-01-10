package com.travis.monolith.module.demo.internal.utils;

import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import com.travis.monolith.module.demo.internal.exception.LockFailedException;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Redis分布式锁
 * Spring Integration实现
 *
 * @author travis
 * @email travis1215@vip.qq.com
 * @date 2020-04-21 21:25
 */
@Component
public class RedisLockUtils {
    private static final String KEY_PREFIX = "redisLock:";
    private static final long DEFAULT_TIME = 3;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    private static RedissonClient redissonClient;

    @Autowired
    public void setRedisLockRegistry(RedissonClient redissonClient) {
        RedisLockUtils.redissonClient = redissonClient;
    }

    /**
     * 阻塞当前进程竞争锁，直到竞争成功（不建议用）
     *
     * @param key
     */
    public static Lock lock(String key) {
        var lock = redissonClient.getLock(KEY_PREFIX + key);
        lock.lock();
        return lock;
    }

    /**
     * 在默认时间内，多次尝试获得锁
     *
     * @param key
     * @return
     * @throws InterruptedException
     */
    public static Lock tryLock(String key) {
        return RedisLockUtils.tryLock(key, DEFAULT_TIME);
    }

    /**
     * 在指定时间内，多次尝试获得锁
     *
     * @param key
     * @param time
     * @return
     * @throws InterruptedException
     */
    public static Lock tryLock(String key, long time) {
        var lock = redissonClient.getLock(KEY_PREFIX + key);
        boolean isLock = false;
        try {
            isLock = lock.tryLock(time, DEFAULT_TIME_UNIT);
        } catch (InterruptedException ignored) {
        }
        if (isLock) {
            return lock;
        }
        throw new LockFailedException(ErrorCodeEnum.GET_LOCK_FAILED);
    }

    /**
     * 竞争锁，如果成功就返回true，失败就返回false，只尝试一次
     *
     * @param key
     * @return
     */
    public static Lock tryLockOnce(String key) {
        var lock = redissonClient.getLock(KEY_PREFIX + key);
        var isLock = lock.tryLock();
        if (isLock) {
            return lock;
        }
        throw new LockFailedException(ErrorCodeEnum.GET_LOCK_FAILED);
    }


}
