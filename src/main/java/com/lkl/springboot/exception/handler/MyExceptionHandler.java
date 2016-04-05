package com.lkl.springboot.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常处理策略  作为父类，让controller继承
 * 
 * @author lkl
 * @version $Id: MyExceptionHandler.java, v 0.1 2015年7月28日 下午10:35:21 lkl Exp $
 */
public class MyExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public void handleRunTimeException(RuntimeException r, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(" request.getContentType():" + request.getContentType());
        System.out.println(" response.getStatus():" + response.getStatus());
        System.out.println(" MyExceptionHandler:" + r.getMessage());
        //    return "some error";

    }

}
