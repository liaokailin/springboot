package com.lkl.springboot.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 生命周期bean测试
 * 实现SmartLifecycle接口，并且 isAutoStartup 设置为true。isRunning 返回为false，在启动时候会调用start 方法
 * 具体实现代码见 {@link AbstractApplicationContext#refresh()} 
 * -> finishRefresh ->initLifecycleProcessor();-> getLifecycleProcessor().onRefresh();
 * 
 * ++++ getPhase
   ++++ getPhase
   ++++ isRunning
   ++++ start
 * 
 * @author liaokailin
 * @version $Id: MyLifecycle.java, v 0.1 2015年10月24日 下午2:51:38 liaokailin Exp $
 */
@Component
public class MyLifecycle implements SmartLifecycle {
    private Logger log = LoggerFactory.getLogger(MyLifecycle.class);

    @Override
    public void start() {
        log.info("++++ start");
    }

    @Override
    public void stop() {
        log.info("++++ stop");
    }

    @Override
    public boolean isRunning() {
        log.info("++++ isRunning");
        return false;
    }

    @Override
    public int getPhase() {
        log.info("++++ getPhase");
        return 0;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        log.info("++++ stop");
    }

}
