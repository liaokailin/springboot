package com.lkl.springboot.concurrent.limit;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.StopWatch;

/**
 * 并发限流操作
 * SimpleAsyncTaskExecutor 为TaskExecutor子类，内部通过 ConcurrencyThrottleAdapter实现并发的限流操作
 * @author liaokailin
 * @version $Id: MySimpleAsyncTaskExecutor.java, v 0.1 2015年10月24日 下午10:04:53 liaokailin Exp $
 */
public class MySimpleAsyncTaskExecutor {
    public static final Integer           MAX_TASK_NUM   = 100;
    public static final Integer           MAX_THREAD_NUM = 5;
    public static SimpleAsyncTaskExecutor executor       = new SimpleAsyncTaskExecutor();
    public static CountDownLatch          latch          = new CountDownLatch(MAX_TASK_NUM);
    public static Semaphore               semaphore      = new Semaphore(MAX_THREAD_NUM);

    public static void main(String[] args) {
        long total = 0l;
        for (int i = 0; i < 50; i++) {
            //  total += testSemaphore();
            total += testSimpleAsyncTaskExecutor();
        }
        System.out.println("+++++++++++++++==>" + total / 50);
    }

    public static long testSemaphore() {

        StopWatch watch = new StopWatch();
        watch.start();
        for (int i = 0; i < MAX_TASK_NUM; i++) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.MILLISECONDS.sleep(1);
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        watch.stop();
        System.out.println("++++total take " + watch.getTotalTimeMillis() + "ms");
        return watch.getTotalTimeMillis();

    }

    public static long testSimpleAsyncTaskExecutor() {
        StopWatch watch = new StopWatch();
        watch.start();
        executor.setConcurrencyLimit(MAX_THREAD_NUM); //
        for (int i = 0; i < MAX_TASK_NUM; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.MILLISECONDS.sleep(1);
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        watch.stop();
        System.out.println("++++total take " + watch.getTotalTimeMillis() + "ms");
        return watch.getTotalTimeMillis();
    }

}
