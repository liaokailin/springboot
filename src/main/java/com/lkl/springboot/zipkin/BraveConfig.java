package com.lkl.springboot.zipkin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.EmptySpanCollectorMetricsHandler;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.HttpSpanCollector;
import com.github.kristofa.brave.servlet.BraveServletFilter;

/**
 * Created by liaokailin on 16/7/27.
 */
@Configuration
public class BraveConfig {

    @Bean
    public BraveServletFilter braveServletFilter() {
        Brave.Builder builder = new Brave.Builder("spring-boot-demo");
        builder.spanCollector(HttpSpanCollector.create("http://110.173.14.57:9411/",
            new EmptySpanCollectorMetricsHandler()));
        Brave brave = builder.build();
        BraveServletFilter filter = new BraveServletFilter(brave.serverRequestInterceptor(),
            brave.serverResponseInterceptor(), new DefaultSpanNameProvider());
        return filter;
    }
}
