package com.lkl.springboot.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步调用  @EnableAsync
 * 定时调度：@EnableScheduling
 * @author lkl
 * @version $Id: AsynCall.java, v 0.1 2015年7月29日 上午11:30:32 lkl Exp $
 */
@Component
public class AsyncCall {

    @Async
    public void asyncMethod() {
        System.out.println("---  asyn method start--");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---  asyn method end--");

    }

}
