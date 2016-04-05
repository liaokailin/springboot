package com.lkl.springboot.listener;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        System.out.println(" ApplicationFailedEventListener ====>" + event.getTimestamp());
        System.out.println(" event.getException():===>" + event.getException().getMessage());
        System.out.println("---------------------------------------");

    }
}
