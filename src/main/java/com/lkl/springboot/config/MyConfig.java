package com.lkl.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lkl.springboot.config.AppConfig;
import com.lkl.springboot.config.AppConfig2;
import com.lkl.springboot.config.AppConfig3;

@Component
public class MyConfig {

    @Autowired
    private AppConfig3 appConfig3;

    @Autowired
    private AppConfig2 appConfig2;

    @Autowired
    private AppConfig  appConfig;

    public void showAppConfig() {
        System.out.println("appConfig:" + appConfig);
        System.out.println("appConfig2:" + appConfig2);
        System.out.println("appConfig3:" + appConfig3);
    }

}
