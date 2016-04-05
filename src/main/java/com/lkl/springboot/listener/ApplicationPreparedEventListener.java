package com.lkl.springboot.listener;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 该监听执行完成时 bean未初始化完成  获取不到所有的bean， 可以采用@PostConstruct（bean肯定初始化完成），或采用 @see {@link ApplicationContextAware}
 * 
 * @author lkl
 * @version $Id: ApplicationPreparedEventListener.java, v 0.1 2015年7月27日 下午7:57:25 lkl Exp $
 */
public class ApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        System.out.println("ApplicationPreparedEventListner ==>" + event.getTimestamp());
        ConfigurableApplicationContext cac = event.getApplicationContext();
        if (cac != null) {
            System.out.println("-----cac.isActive():" + cac.isActive());
            int beanCount = cac.getBeanDefinitionCount();
            System.out.println("-----beanCount : " + beanCount);

            String[] beanNames = cac.getBeanDefinitionNames();
            if (beanNames != null) {
                for (String s : beanNames) {
                    System.out.println("beanName :" + s);
                }
            }
        }

        System.out.println("---------------------------------------");
    }

}
