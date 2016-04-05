package com.lkl.springboot.archaius;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;
import com.netflix.config.AbstractPollingScheduler;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;

/**
 * Archaius默认加载config.properties
 * 
 * @author liaokailin
 * @version $Id: LoadProperties.java, v 0.1 2016年1月8日 下午2:05:14 liaokailin Exp $
 */
public class LoadProperties {

    public static void main(String[] args) throws InterruptedException {
        DynamicLongProperty timeToWait = DynamicPropertyFactory.getInstance().getLongProperty("lock.waitTime", 1000l);
        System.out.println(timeToWait.get());

        //拓展
        AbstractPollingScheduler scheduler = new MyAbstractPollingScheduler();//实现子类 自定义如何定时拉去
        //
        PolledConfigurationSource source = new MyPolledConfigurationSource();//定义数据来源

        DynamicConfiguration configuration = new DynamicConfiguration(source, scheduler);

        ConfigurationManager.install(configuration);

        //通过configuration获取数据
        //所有的数据
        /**
         * for (Iterator<Object> iter = configuration.getKeys(); iter.hasNext();) {
            String key = iter.next().toString();
            System.out.println("key -> " + key + " value ->" + configuration.getProperty(key));
        }
         */
        while (true) {
            System.out.println("----" + configuration.getProperty("random"));
            TimeUnit.SECONDS.sleep(4);
        }

    }

    /**
     * 拓展使用
     *  AbstractPollingScheduler scheduler = null;//实现子类 自定义如何定时拉去
        //
        PolledConfigurationSource source = null;//定义数据来源

        DynamicConfiguration configuration = new DynamicConfiguration(source, scheduler);

        ConfigurationManager.install(configuration);

        //通过configuration获取数据

        configuration.getKeys();//所有的数据
     * 
     * @author liaokailin
     * @version $Id: LoadProperties.java, v 0.1 2016年1月8日 下午3:06:09 liaokailin Exp $
     */
    static class MyAbstractPollingScheduler extends AbstractPollingScheduler { //可以参考FixedDelayPollingScheduler 

        private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

        /**
         * runnable  表示拉取配置的操作
         * @see com.netflix.config.AbstractPollingScheduler#schedule(java.lang.Runnable)
         */
        @Override
        protected void schedule(Runnable runnable) {
            service.scheduleAtFixedRate(runnable, 1, 2, TimeUnit.SECONDS);
        }

        @Override
        public void stop() {

        }

    }

    static class MyPolledConfigurationSource implements PolledConfigurationSource {

        /**
         * initial true表示第一次拉取
         * checkPoint 检查点对象,用于确定如果返回的结果是增量的起点
         * 
         * @see com.netflix.config.PolledConfigurationSource#poll(boolean, java.lang.Object)
         */
        @Override
        public PollResult poll(boolean initial, Object checkPoint) throws Exception {
            Map<String, Object> map = Maps.newHashMap();
            Random random = new Random();

            map.put("random", random.nextInt(10000));
            return PollResult.createFull(map);
        }

    }
}
