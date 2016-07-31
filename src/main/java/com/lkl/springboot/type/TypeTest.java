package com.lkl.springboot.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;

public class TypeTest implements ApplicationContextAware {

    @Test
    public void testParameterizedType() {
        Set<String> s = new HashSet<String>();
        //s.getClass().getGenericSuperclass().getClass().getName() : ParameterizedType
        System.out.println(s.getClass().getGenericSuperclass().getClass().getName());
        System.out.println(ParameterizedType.class.isAssignableFrom(s.getClass().getGenericSuperclass().getClass()));
        Type[] ts = s.getClass().getGenericInterfaces();
        for (Type t : ts) {
            System.out.println(t.getClass().getName());
        }
    }

    /**
     * ResolvableType#hasGenerics() 判断是否存在泛型化参数
     */
    @Test
    public void testResolvableType() {
        Set<String> s = new HashSet<String>();
        ResolvableType c = ResolvableType.forClass(s.getClass());
        System.out.println(c.hasGenerics()); // true
        ResolvableType rt = c.getGeneric(0);//获取泛型，由jdk的动态代理生成
        c = ResolvableType.forClass(TypeTest.class);
        System.out.println(c.hasGenerics()); //false
    }

    @Test
    public void testResolvableType2() {
        ResolvableType c = ResolvableType.forClass(Set.class);
        System.out.println(c.getGeneric().resolve());
    }

    public void genericArrayTypeMethod(String[] ss) {
    }

    @Test
    public void test() {
        System.out.println(TypeTest.class.isInterface());
        System.out.println(Proxy.isProxyClass(TypeTest.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // TODO Auto-generated method stub

    }
}
