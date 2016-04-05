package com.lkl.springboot.health;

import org.springframework.boot.actuate.autoconfigure.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.RabbitHealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 自定义健康指示器
 * 如果在project中存在rabbitmq的jar包，但是rabbitmq服务器没启，在启动时候会调用 {@link RabbitHealthIndicator}
 * 类去校验rabbitmq信息，会抛出异常，在配置文件中应该去掉rabbit的健康检查
 * management.health.rabbit.enabled=false 
 * 详细类见：{@link HealthIndicatorAutoConfiguration}
 * 
 * @author lkl
 * @version $Id: MyHealthCheck.java, v 0.1 2015年8月12日 下午7:19:46 lkl Exp $
 */
@Component
public class MyHealthCheck implements HealthIndicator {
    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    public int check() {
        return 0;
    }
}