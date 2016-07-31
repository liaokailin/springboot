package com.lkl.springboot;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.lkl.springboot.config.MyConfig;
import com.lkl.springboot.domain.User;
import com.lkl.springboot.listener.ApplicationEnvironmentPreparedEventListener;
import com.lkl.springboot.listener.ApplicationFailedEventListener;
import com.lkl.springboot.listener.ApplicationPreparedEventListener;
import com.lkl.springboot.listener.ApplicationStartedEventListener;

/**
 * 默认加载application.properties文件
 * 
 * @author lkl
 * @version $Id: Application.java, v 0.1 2015年7月27日 下午8:55:14 lkl Exp $
 */
@SpringBootApplication
// same as @Configuration @EnableAutoConfiguration @ComponentScan
//public class Application extends MyExceptionHandler implements DisposableBean {
public class Application implements DisposableBean {
    //启用log输出
    private Logger   log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private User     user;

    @Autowired
    private MyConfig myConfig;

    /**
     * the init method will not be called until any autowiring is done for the bean
     */
    @PostConstruct
    public void init() {
        System.out.println(getClass().getSimpleName() + ": init");
        System.out.println(user);
        myConfig.showAppConfig();
    }

    /**
     * register a shutdown hook  优雅退出，或者实现{@link DisposableBean} 接口
     * @call {@link ConfigurableApplicationContext#close()}
     * 
     */
    @PreDestroy
    public void preDestroy() {
        System.out.println("exit by annotation");
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);

        /**
         * add Listeners
         * ApplicationStartedEventListener -> ApplicationEnvironmentPreparedEventListner -> ApplicationPreparedEventListner
         */
        app.addListeners(new ApplicationStartedEventListener());
        app.addListeners(new ApplicationEnvironmentPreparedEventListener());
        app.addListeners(new ApplicationPreparedEventListener());
        app.addListeners(new ApplicationFailedEventListener());
        ConfigurableApplicationContext act = app.run(args);
        // app.setAdditionalProfiles();
        //   act.close();

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("exit by implements DisposableBean");
    }

}
