package com.lkl.springboot.rxjava;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Notification;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.BlockingObservable;
import rx.schedulers.Schedulers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * RxJava:在Java VM上使用可观测的序列来组成异步的、基于事件的程序库
 * <p/>
 * <p/>
 * 观察者：  Observer
 * 被观察者：Observable
 * Observable和Observer通过subscribe来关联 Observer可以理解为Subscriber订阅者
 * <p/>
 * Created by liaokailin on 16/1/6.
 */
public class SampleDemo {

    public static final Logger LOGGER = LoggerFactory.getLogger(SampleDemo.class);

    @Test
    public void doOnTerminate() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.info("onError");
            }

            @Override
            public void onNext(String string) {

                LOGGER.info("onNext {}", string);

            }
        };

        //创建被观察者
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello world");
                subscriber.onCompleted(); //
            }

        });

        Observable<String> newOb = observable.doOnTerminate(new Action0() {

            @Override
            public void call() {
                System.out.println("realease resource "); //在源observable调用onCompleted 或者onError方法时 都会调用
                //释放资源  不管是成功或者失败 都会调用该方法
            }
        });
        newOb.subscribe(observer);
    }

    @Test
    public void baseConcept() {
        //创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.info("onError");
            }

            @Override
            public void onNext(String string) {

                LOGGER.info("onNext {}", string);

            }
        };

        //创建被观察者
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello world");
            }
        });

        observable.subscribe(observer);

        Observable.just("hello", "hhhh").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                LOGGER.info("++++{}", s);
            }
        });

        /**
         *
         * Observable # subscribeOn 设置被订阅者发生的线程
         * Observable  #  observeOn 设置订阅者发生的线程
         *
         * Schedulers.immediate():当前线程
         * Schedulers.newThread(): 启动新线程
         * Schedulers.computation(): 适合计算密集型 使用固定的线程池 大小为cpu核心数
         * Schedulers.io(): io密集型 内部实现为县城数无上限的线程池
         */
        Observable.just("1", "2", "3").subscribeOn(Schedulers.immediate()).observeOn(Schedulers.computation())
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    LOGGER.info("thread name is {} ,content is {}", Thread.currentThread().getName(), s);
                }
            });

        //  map flatmap

        /**
         * map作用:将Observable被观察者发出的事件信息进行处理 再返回 可通过FuncX等接口去包装返回的类型
         */
        Observable.just("1", "2", "3").map(new Func1<String, List<String>>() {
            private List<String> list = Lists.newArrayList();

            @Override
            public List<String> call(String s) {
                list.add(s);
                return list;
            }
        }).subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> list) {
                LOGGER.info("list result is {}", list.toString());
            }
        });

        Map<String, String> map1 = Maps.newHashMap();
        map1.put("1", "1");
        map1.put("2", "2");
        map1.put("3", "3");

        Map<String, String> map2 = Maps.newHashMap();
        map2.put("11", "11");
        map2.put("12", "12");
        map2.put("13", "13");
        //打印所有的value
        //实现1：
        Observable.just(map1, map2).map(new Func1<Map<String, String>, String>() {
            @Override
            public String call(Map<String, String> map) {
                StringBuilder sb = new StringBuilder();
                for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
                    sb.append(iter.next()).append(",");
                }
                return sb.toString();
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                LOGGER.info("++ {}", s);
            }
        });

        /**
         * map和flatmap都是将传入的参数转化后返回另外一个对象，flatMap返回Observable对象，返回的Observable对象不是直接返回给Observe/subscriber(观察者)，而是将该Observable激活
         * 于是它开始发送事件， 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，而这个 Observable 负责将这些事件统一交给 Subscriber 的回调方法
         *
         */
        //实现2
        Observable.just(map1, map2).flatMap(new Func1<Map<String, String>, Observable<String>>() {
            @Override
            public Observable<String> call(Map<String, String> map) {
                return Observable.from(map.values());
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                LOGGER.info("++flatmap:{}", s);
            }
        });

        System.out.println("hello");

        Observable.just("1", "2", "3", "4").map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return s.hashCode();
            }
        }).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                LOGGER.info("filter-call{}", integer);
                return integer > 10;
            }
        }).take(10).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                LOGGER.info("doOnNext {}", integer);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                LOGGER.info("subscribe {}", integer);
            }
        });

    }

    /**
     * 用于缓存
     */
    @Test
    public void userForCache() {
        Observable cache = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (1 == 2) {
                    subscriber.onNext("cache");
                } else {
                    subscriber.onCompleted();
                }
            }
        });

        Observable localFile = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("local");
            }
        });

        Observable remote = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("remote");
            }
        });

        /**
         * 只需要一个满足即返回结果值
         */
        Observable.concat(cache, localFile, remote).first().subscribe(new Action1<String>() {
            @Override
            public void call(String o) {
                LOGGER.info("===={}", o);
            }
        });
    }

    @Test
    public void userForMulInterfaceReturn() {

        Observable interface01 = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("interface01");
            }
        });

        Observable interface02 = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("interface02");
            }
        });

        Observable.merge(interface01, interface02).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                LOGGER.info(s);
            }
        });

    }

    @Test
    public void dataHandle() {
        Observable.just("1", "2", "2", "3", "3", "4", "4", "5", "5").map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return Integer.parseInt(s);
            }
        }).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer > 1;
            }
        }).distinct().take(6).reduce(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                LOGGER.info("integer1:{} integer2:{}", integer, integer2);
                return integer + integer2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                LOGGER.info("result:{}", integer);
            }
        });

    }

    @Test
    public void testLift() {
        Observable.just("1", "2").lift(new Observable.Operator<Integer, String>() {
            @Override
            public Subscriber<? super String> call(final Subscriber<? super Integer> subscriber) {
                return new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(String s) {
                        subscriber.onNext(Integer.parseInt(s));
                    }
                };
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                LOGGER.info("integer:{}", integer);
            }
        });

    }

    /**
     * Observable.doOnNext 在订阅者(观察者)执行onNext()之前执行
     *
     *
     */
    @Test
    public void testDoOnNextMethod() {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1234");
                subscriber.onNext("hhhh");
            }
        }).doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                LOGGER.info("doOnNext {}", s);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                LOGGER.info("subscribe {}", s);
            }
        });
    }

    /**
     * unsafeSubscribe 不安全的订阅，只是简单的对call操作进行try catch处理
     */
    @Test
    public void testUnsafeSubscribeMethod() {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1234");
                subscriber.onNext("hhhh");
                // throw  new RuntimeException("test exception123");
                //subscriber.onError(new RuntimeException("test exception"));
            }
        }).doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                LOGGER.info("doOnNext {}", s);
            }
        }).unsafeSubscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LOGGER.info("eeeee{}", e.getMessage());
            }

            @Override
            public void onNext(String s) {
                LOGGER.info("hhhhh:{}", s);
            }
        });
    }

    /**
     * ¨doOnEach 通过notification.isOnNext  notification.isOnError notification.isOnCompleted 可判断执行的方法，在方法执行之前若干操作，方法发生在doOnNext之前
     */
    @Test
    public void testDoOnEachMethod() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1234");
                subscriber.onNext("hhhh");
                // throw  new RuntimeException("test exception123");
                subscriber.onError(new RuntimeException("test exception"));
            }
        }).doOnEach(new Action1<Notification<? super String>>() {
            @Override
            public void call(Notification<? super String> notification) {
                if (notification.isOnNext()) {
                    LOGGER.info("notification.isOnNext()", notification.isOnNext());
                }

                if (notification.isOnError()) {
                    LOGGER.info("notification.isOnError()", notification.isOnError());
                }
            }
        }).doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                LOGGER.info("doOnNext {}", s);
            }
        }).unsafeSubscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LOGGER.info("eeeee{}", e.getMessage());
            }

            @Override
            public void onNext(String s) {
                LOGGER.info("hhhhh:{}", s);
            }
        });
    }

    public void toBlock() {

        BlockingObservable<String> stringBlockingObservable = Observable.just("1", "2").toBlocking();
    }

}
