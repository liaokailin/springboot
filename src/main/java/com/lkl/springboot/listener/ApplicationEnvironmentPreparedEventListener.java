package com.lkl.springboot.listener;

import java.util.Iterator;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 *  application环境配置监听
 * @author lkl
 * @version $Id: ApplicationEnvironmentPreparedEventListener.java, v 0.1 2015年7月27日 下午5:12:29 lkl Exp $
 */
public class ApplicationEnvironmentPreparedEventListener implements
                                                        ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        System.out.println(" ApplicationEnvironmentPreparedEventListner ====>" + event.getTimestamp());
        ConfigurableEnvironment envi = event.getEnvironment();
        MutablePropertySources mps = envi.getPropertySources();
        if (mps != null) {
            Iterator<PropertySource<?>> iter = mps.iterator();
            while (iter.hasNext()) {
                PropertySource<?> ps = iter.next();
                if (ps.getName().equals("applicationConfigurationProperties")) {
                    Object o = ps.getProperty("hit");
                    System.out.println("获取资源文件信息:" + o);
                }

                System.out.println("ps: ps.getName()==>" + ps.getName() + ";ps.getSource()==>" + ps.getSource()
                                   + "; ps.getClass()==>" + ps.getClass());
            }
        }
        System.out.println("---------------------------------------");
    }

}
