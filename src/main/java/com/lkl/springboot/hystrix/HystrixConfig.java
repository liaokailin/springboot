package com.lkl.springboot.hystrix;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

/**
 * http://localhost:8080/hystrix.stream
 * 
 * @author liaokailin
 * @version $Id: HystrixConfig.java, v 0.1 2015年11月5日 下午6:45:47 liaokailin Exp $
 */
@Configuration
public class HystrixConfig {

    @Bean
    public HystrixMetricsStreamServlet getHystrixMetricsStreamServlet() {
        return new HystrixMetricsStreamServlet();
    }

    @Bean
    public ServletRegistrationBean registration(HystrixMetricsStreamServlet filter) {
        ServletRegistrationBean registration = new ServletRegistrationBean(filter, new String[0]);
        registration.setEnabled(true);
        registration.addUrlMappings(new String[] { "/hystrix.stream" });
        return registration;

    }

}
