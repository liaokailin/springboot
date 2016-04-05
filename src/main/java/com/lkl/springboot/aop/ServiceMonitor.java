package com.lkl.springboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 服务监控
 * aop 添加jar
 * <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        
 * @author lkl
 * @version $Id: ServiceMonitor.java, v 0.1 2015年8月5日 上午11:18:22 lkl Exp $
 */
//@Aspect
@Component
public class ServiceMonitor {

    private Logger log = LoggerFactory.getLogger(ServiceMonitor.class);

    // @Pointcut("execution(* *..service*..*(..))")
    // @Pointcut("execution(* com.u51.lkl.springboot.service..*Service.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint jp) {
        log.info("-----前置通知 start-----");
        log.info("jp.getSignature().getDeclaringTypeName():{}", jp.getSignature().getDeclaringTypeName());
        log.info("methodName:" + jp.getSignature().getName());
        log.info(jp.getArgs() != null ? jp.getArgs().toString() : "");
        log.info(jp.getTarget().toString());
        log.info("-----前置通知 end-----");
    }

    @After("pointcut()")
    public void after(JoinPoint jp) {
        log.info("----after----");
    }

    @AfterReturning("pointcut()")
    public void afterReturning(JoinPoint point, Object returnValue) {
        log.info("-----@AfterReturning-----" + returnValue);
    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint point) {
        try {
            System.out.println("-----around-start-----");
            point.proceed();
            System.out.println("-----around-end-----");
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {

    }

}
