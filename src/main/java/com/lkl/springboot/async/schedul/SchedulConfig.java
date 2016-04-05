package com.lkl.springboot.async.schedul;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.lkl.springboot.async.AsyncSchedulConfig;

/**
 * 定时调度，启动下面的两个注解
 * {@link AsyncSchedulConfig}
 * 
 * @author lkl
 * @version $Id: SchedulConfig.java, v 0.1 2015年7月29日 下午2:18:32 lkl Exp $
 */
//@Configuration
//@EnableScheduling
public class SchedulConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }

}
