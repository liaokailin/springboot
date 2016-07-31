package com.lkl.springboot.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZipkinController {

    @RequestMapping(value = "/zipkin")
    public String zipkin() throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        System.out.println("hello zipkin.");
        return "hello zipkin";
    }
}
