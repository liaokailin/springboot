package com.u51.lkl.springboot.test;

import org.springframework.context.ApplicationListener;
import org.springframework.core.GenericTypeResolver;

import com.lkl.springboot.listener.ApplicationStartedEventListener;

public class GenericTypeResolverTest {

    public static void main(String[] args) {

        /**
         * GenericTypeResolver: 泛型解析器
         * GenericTypeResolver.resolveTypeArgument
         * 获取实现的接口，或者父类中的泛型类型
         */
        Class<?> clazz = GenericTypeResolver.resolveTypeArgument(ApplicationStartedEventListener.class,
            ApplicationListener.class);
        System.out.println(clazz); //class org.springframework.boot.context.event.ApplicationStartedEvent

        System.out.println(ApplicationListener.class.isAssignableFrom(ApplicationStartedEventListener.class));
    }

}
