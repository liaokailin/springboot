package com.lkl.springboot.hystrix;

import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

//重载HystrixCommand 的getFallback方法实现逻辑
public class HelloWorldCommand02 extends HystrixCommand<String> {
    private final String name;

    public HelloWorldCommand02(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
        /* 配置依赖超时时间,500毫秒*/
        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)));
        this.name = name;
    }

    @Override
    protected String getFallback() {
        return "exeucute Falled";
    }

    /**
     * 前面设置500ms超时，当这里修改1000ms后，则会执行getFallback()方法 
     * 
     * 除了HystrixBadRequestException异常之外，所有从run()方法抛出的异常都算作失败，并触发降级getFallback()和断路器逻辑。
          HystrixBadRequestException用在非法参数或非系统故障异常等不应触发回退逻辑的场景
     */
    @Override
    protected String run() throws Exception {
        //sleep 1 秒,调用会超时
        TimeUnit.MILLISECONDS.sleep(1000);
        return "Hello " + name + " thread:" + Thread.currentThread().getName();
    }

    public static void main(String[] args) throws Exception {
        HelloWorldCommand02 command = new HelloWorldCommand02("test-Fallback");
        String result = command.execute();
        System.out.println(result);
    }
}
/* 运行结果:getFallback() 调用运行
getFallback executed
*/