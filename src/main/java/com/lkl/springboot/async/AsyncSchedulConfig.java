package com.lkl.springboot.async;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.lkl.springboot.async.AsynConfig;
import com.lkl.springboot.async.schedul.SchedulConfig;

/**
 * 同时配置定时调度和异步调度
 * 单独配置定时调度 {@link SchedulConfig}
 * 单独配置异步调度 {@link AsynConfig}
 * @author lkl
 * @version $Id: AsyncSchedulConfig.java, v 0.1 2015年7月29日 下午2:16:59 lkl Exp $
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncSchedulConfig implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("threadName:" + t.getName() + ";e:" + e.getMessage());
    }

    @Bean
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("MyExecutor-");
        executor.initialize();
        return executor;
    }

}
