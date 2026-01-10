package com.travis.monolith.module.demo.internal.config.properties.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author travis
 */

@Component
@Data
@ConfigurationProperties(prefix = "application.thread-pool.task-executor")
public class TaskExecutorProperties {
    /**
     * 核心线程数
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maxPoolSize;
    /**
     * 队列容量
     */
    private int queueCapacity;
    /**
     * 线程活跃时间（秒）
     */
    private int keepAliveSeconds;

}
