package com.lkl.springboot.clr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 多CommandLineRunner实现，可通过Order指定执行顺序
 * 
 * @author lkl
 * @version $Id: MyCommandLineRunner02.java, v 0.1 2015年7月27日 下午8:30:52 lkl Exp $
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyCommandLineRunner02 implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("MyCommandLineRunner02");

    }

}
