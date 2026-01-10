package com.travis.monolith.module.demo.internal.config;

import com.travis.monolith.module.demo.internal.config.properties.threadpool.TaskExecutorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author travis
 * @date 2019-04-09 01:27
 */

@Configuration
@EnableAsync
public class ThreadPoolTaskConfig {
    private final TaskExecutorProperties taskExecutorProperties;

    @Autowired
    public ThreadPoolTaskConfig(TaskExecutorProperties taskExecutorProperties) {
        this.taskExecutorProperties = taskExecutorProperties;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(taskExecutorProperties.getCorePoolSize());
        // 设置最大线程数
        executor.setMaxPoolSize(taskExecutorProperties.getMaxPoolSize());
        // 设置队列容量
        executor.setQueueCapacity(taskExecutorProperties.getQueueCapacity());
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(taskExecutorProperties.getKeepAliveSeconds());
        // 设置默认线程名称
        executor.setThreadNamePrefix("ThreadPoolTask-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
