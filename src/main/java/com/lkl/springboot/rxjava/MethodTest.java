package com.lkl.springboot.rxjava;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Notification;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;

public class MethodTest {
    public static final Logger LOGGER     = LoggerFactory.getLogger(MethodTest.class);
    private boolean            flag       = true;

    private Observer<String>   observer   = new Observer<String>() { //观察者
                                              @Override
                                              public void onCompleted() {
                                                  LOGGER.info("订阅者的onCompleted");
                                              }

                                              @Override
                                              public void onError(Throwable throwable) {
                                                  LOGGER.info("订阅者的onError", throwable);
                                              }

                                              @Override
                                              public void onNext(String string) {

                                                  LOGGER.info("订阅者的onNext {}", string);

                                              }
                                          };

    private Observable<String> observable = Observable.create(new OnSubscribe<String>() {

                                              @Override
                                              public void call(Subscriber<? super String> t) {
                                                  t.onNext("Hello 我是观察者发出的值 ");
                                                  if (flag) {
                                                      t.onCompleted();
                                                  } else {
                                                      t.onError(new RuntimeException("test error."));
                                                  }
                                              }
                                          });

    /**
     *  在observable被观察者(事件源)中存在OnSubscribe属性 ，调用observable.subscribe即调用OnSubscribe对应的call方法 call方法接受参数为观察者/订阅者Subsriber
     */
    @Test
    public void testBasic() {

        observable.subscribe(observer);
    }

    /**
     * 已map来说明lift：observable.map() 整体为Observable，其OnSubscribe#call(订阅者)方法 将订阅者传递到Operator中返回一个新的订阅者，
     * 然后通过源被观察者(Observable)中的OnSubscribe#call(新订阅者)。
     * 
     * lift 创建一个新的Subscriber去订阅被观察者。 新的观察者由Operator#call构成。 新的观察者中的方法(onNext,onError,onCompleted)通过原始被观察者创建时的onSubscribe#call去调用
     */
    @Test
    public void testMap() {

        observable.map(new Func1<String, Integer>() {

            @Override
            public Integer call(String s) {
                try {
                    return Integer.valueOf(s);
                } catch (Exception e) {
                    LOGGER.info("转换异常 ");
                }
                return 12;
            }
        }).subscribe(new Action1<Integer>() {

            @Override
            public void call(Integer t) {
                LOGGER.info("Integer:" + t);
            }
        });
    }

    /**
     * 只有在原始Observable中属性OnSubscribe#call调用Subscriber的onError 或者 onCompleted 方法时 doOnTerminate下的 {@link Action0#call()}方法才会被执行。
     * note:Observable只有在被订阅时才会执行对应方法
     */
    @Test
    public void testDoOnTermimate() {
        observable.doOnTerminate(new Action0() {

            @Override
            public void call() {
                LOGGER.info("doOnTerminate");
            }
        }).unsafeSubscribe(new Subscriber<String>() {

            @Override
            public void onCompleted() {
                LOGGER.info("unsafeSubscribe: onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.info("unsafeSubscribe: onError", e);
            }

            @Override
            public void onNext(String t) {
                LOGGER.info("unsafeSubscribe: onNext", t);
            }
        });
    }

    /**
     * doOnEach 在执行观察者的每个方法时候 都可以通过Notification将对应事件发射出去
     */
    @Test
    public void testDoOnEach() {
        observable.doOnEach(new Action1<Notification<? super String>>() {

            @Override
            public void call(Notification<? super String> t) {

                if (t.isOnNext()) {
                    LOGGER.info("isOnNext,the value is  {}", t.getValue());
                }

                if (t.isOnCompleted()) {
                    LOGGER.info("isOnCompleted");
                }
            }
        }).subscribe(observer);
    }

    /**
     * 执行观察者之前执行的方法。可以优先获取到被观察者发出的值
     */
    @Test
    public void testDoOnNext() {
        observable.doOnNext(new Action1<String>() {

            @Override
            public void call(String t) {
                LOGGER.info("执行onNext方法之前获取到被观察者的值:" + t);
            }
        }).subscribe(observer);
    }

    /**
     * 执行onCompleted方法之前执行的方法
     */
    @Test
    public void testDoOnCompleted() {
        observable.doOnCompleted(new Action0() {

            @Override
            public void call() {
                LOGGER.info("执行onCompleted方法之前执行的方法");
            }
        }).subscribe(observer);
    }

    /**
     * 在原被观察者中调用观察者的onError方法时，创建一个新的Observable让观察者去订阅。
     */
    @Test
    public void testOnErrorResumeNext() {
        this.flag = false;
        observable.onErrorResumeNext(new Func1<Throwable, Observable<? extends String>>() {

            @Override
            public Observable<? extends String> call(final Throwable t) {
                return Observable.create(new OnSubscribe<String>() {

                    @Override
                    public void call(Subscriber<? super String> str) {
                        str.onNext("传递了新的值");
                        LOGGER.info("onErrorResumeNext:{}", t.getMessage());
                    }
                });

            }
        }).subscribe(observer);
    }

    @Test
    public void testUnsubscribe() {
        observable.subscribe(observer).unsubscribe(); //在异步下 被观察者没有完成钱 观察者可以取消订阅。
    }

    @Test
    public void testRepay() {
        ReplaySubject<Object> subject = ReplaySubject.create();

        subject.onNext("one");
        subject.onNext("two");
        subject.onNext("three");
        subject.onCompleted();
        // both of the following will get the onNext/onCompleted calls from above

        observable.subscribe(subject);
        subject.subscribe(new Action1<Object>() {

            @Override
            public void call(Object t) {
                System.out.println(t.toString());
            }
        });
    }

    /**
     * CompositeSubscription
     * 
     * 如果你一直在关注代码，你可能会注意到你调用的 Observable.subscribe() 的返回值是一个 Subscription 对象
     * 。Subscription 类只有两个方法，unsubscribe() 和 isUnsubscribed()。
     * 为了防止可能的内存泄露，在你的 Activity 或 Fragment 的 onDestroy 里，
     * 用 Subscription.isUnsubscribed() 检查你的 Subscription 是否是 unsubscribed。
     * 如果调用了 Subscription.unsubscribe() ，Unsubscribing将会对 items 停止通知给你的 Subscriber，并允许垃圾回收机制释放对象，
     * 防止任何 RxJava 造成内存泄露。如果你正在处理多个 Observables 和 Subscribers，所有的 Subscription 对象可以添加到 CompositeSubscription，
     * 然后可以使用 CompositeSubscription.unsubscribe() 方法在同一时间进行退订(unsubscribed)
     */

    /**
     * 线程
     * 
     *   Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
     *   Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
     *   Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。
     *                   行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，
     *                  因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
     *   Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     *   
     *   subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
     *   observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
     * @throws InterruptedException 
     */
    @Test
    public void testThread() throws InterruptedException {
        /**
         * 被观察者在io性质线程下执行
         * 观察者在当前线程中执行
         */
        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.immediate()).subscribe(observer);
        TimeUnit.SECONDS.sleep(1); //没有休眠 则可能无打印值
    }

}
