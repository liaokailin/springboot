package com.lkl.springboot.async;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步调度，启动下面两个注解
 * {@link AsyncSchedulConfig}
 * 
 * @author lkl
 * @version $Id: AsynConfig.java, v 0.1 2015年7月29日 下午2:17:56 lkl Exp $
 */
//@Configuration
//@EnableAsync
public class AsynConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("MyExecutor-");
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {

            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                System.out.println("method:" + method.getName() + ";ex:" + ex.getMessage());
            }

        };
    }
}
