package com.example.concurrent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {


    @Bean(name = "threadPoolA")
    public ThreadPoolTaskExecutor ThreadPoolTaskExecutormyTaskAsyncPoolA() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(4);
        //最大线程数
        executor.setMaxPoolSize(8);
        //任务队列
        executor.setQueueCapacity(100);
        //空闲线程存活时间  线程执行时间短 任务多时调大 ，提高线程使用率
        executor.setKeepAliveSeconds(60);
        //线程名前缀
        executor.setThreadNamePrefix("Pool-A");

        //策略 新手用 AbortPolicy
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();

        return executor;
    }

    @Bean(name = "ThreadPoolB")
    public ThreadPoolTaskExecutor ThreadPoolTaskExecutorAsyncPoolB() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(2);

        executor.setMaxPoolSize(4);

        executor.setQueueCapacity(8);

        executor.setKeepAliveSeconds(60);

        executor.setThreadNamePrefix("Pool-B");
        //当任务数量超过MaxPoolSize和QueueCapacity时使用的策略，该策略是又调用任务的线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();

        return executor;

    }


}
