package com.lkl.springboot.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Service;

/**
 * 自定义计量器
 *   CounterService  提供自增/减值 
 *   GaugeService 提供绝对值
 * @author lkl
 * @version $Id: CusMetric.java, v 0.1 2015年8月12日 下午7:52:42 lkl Exp $
 */
@Service
public class CusMetric {

    @Autowired
    private CounterService counterService;
    @Autowired
    private GaugeService   gaugeService;

    public void doLogin() {
        this.counterService.increment("counter.login.count");
        this.getAge();
    }

    public void getAge() {
        this.gaugeService.submit("gauge.age", 25);
    }

}
