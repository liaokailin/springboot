package com.lkl.springboot.async.schedul;

import org.springframework.stereotype.Component;

/**
 * 定时调度，无需方法调用
 * 
 * @author lkl
 * @version $Id: SchedulCall.java, v 0.1 2015年7月29日 下午2:00:49 lkl Exp $
 */
@Component
public class SchedulCall {

    // @Scheduled(fixedRate = 1000)
    public void schedulMethod() {
        System.out.println("---schedulMethod at fixedRate 1000");
    }
}
