package com.lkl.springboot.hystrix;

import rx.Observable;
import rx.functions.Action1;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class CommandHelloWorld01 extends HystrixCommand<String> {
    private String name;

    public CommandHelloWorld01(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "hello" + name;
    }

    public static void main(String[] args) {

        String s = new CommandHelloWorld01("Bob").execute();
        System.out.println(s);
        // Future<String> s = new CommandHelloWorld("Bob").queue();
        Observable<String> obs = new CommandHelloWorld01("Bob").observe(); //RxJava
        obs.subscribe(new Action1<String>() {

            @Override
            public void call(String t) {
                System.out.println(t);
            }
        });
    }

}
