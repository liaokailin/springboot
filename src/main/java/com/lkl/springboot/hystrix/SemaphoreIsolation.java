package com.lkl.springboot.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class SemaphoreIsolation extends HystrixCommand<String> {
    private final String name;

    public SemaphoreIsolation(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
        /* 配置信号量隔离方式,默认采用线程池隔离 */
        .andCommandPropertiesDefaults(
            HystrixCommandProperties.Setter().withExecutionIsolationStrategy(
                HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "HystrixThread:" + Thread.currentThread().getName();
    }

    public static void main(String[] args) throws Exception {
        SemaphoreIsolation command = new SemaphoreIsolation("semaphore");
        String result = command.execute();
        System.out.println(result);
        System.out.println("MainThread:" + Thread.currentThread().getName());
    }
}
/** 运行结果
 HystrixThread:main
 MainThread:main
*/
