package com.lkl.springboot.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 应用启动监听，第一个执行的监听事件
 * 
 * @author lkl
 * @version $Id: ApplicationStartedEventListener.java, v 0.1 2015年7月27日 下午5:08:52 lkl Exp $
 */
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        SpringApplication app = event.getSpringApplication();
        app.setShowBanner(false); //启动前 设置不显示banner
        System.out.println(" ApplicationStartedEventListener ====>" + event.getTimestamp());
        System.out.println("---------------------------------------");
    }

}
