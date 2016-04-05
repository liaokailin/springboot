package com.lkl.springboot.controller;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lkl.springboot.Application;
import com.lkl.springboot.amqp.Send;
import com.lkl.springboot.annotation.NoAuth;
import com.lkl.springboot.async.AsyncCall;
import com.lkl.springboot.domain.Order;
import com.lkl.springboot.domain.User;
import com.lkl.springboot.exception.handler.MyControllerAdvice;
import com.lkl.springboot.hystrix.CommandHelloWorld01;
import com.lkl.springboot.service.PersonService;

@RestController
@RequestMapping("/api")
//public class Application extends MyExceptionHandler implements DisposableBean {
public class MyController implements ApplicationContextAware {
    private Logger    log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private AsyncCall asynCall;

    @NoAuth
    @RequestMapping("/home")
    @ResponseBody
    User home() {
        System.out.println("call home method");
        User response = new User();
        response.setAge(25);
        response.setUserName("liaokailin");
        response.setBirthday(new Date());
        //if (response.getAge() == 25)
        //   throw new RuntimeException();
        new CommandHelloWorld01("Bob").execute();
        return response;
    }

    /**
     * 异步方法调用
     * 
     */
    @RequestMapping("/async")
    String async() {
        System.out.println("----sync method start-----");
        asynCall.asyncMethod();
        System.out.println("----sync method end-----");
        return "hello world";
    }

    /**
     * 方法验证
     */
    @RequestMapping(value = "/validator")
    void validator(@Valid Order order, BindingResult result) {
        System.out.println("------validator-----");
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            if (!CollectionUtils.isEmpty(errorList)) {
                for (ObjectError e : errorList) {
                    log.error("error code is {} ,error default message is {}", e.getCode(), e.getDefaultMessage());
                }
            }
        }
        log.info("order info is : {}", order);
    }

    /**
     * 验证
     * 无BindingResult result 或者 Errors 去接受错误消息的时候，
     * 组合 @RequestBody @Valid ，此时会抛出异常 MethodArgumentNotValidException 
     * 默认走DefaultHandlerExceptionResolver进行处理，这里通过 {@link MyControllerAdvice}去处理
     */
    @RequestMapping(value = "/validator2")
    void validatorNoBindingResult(@Valid @RequestBody Order order) {
        log.info("order info is : {}", order);
    }

    @Autowired
    Send send;

    @ResponseBody
    @RequestMapping(value = "/amqp")
    String amqp() {
        send.sendMsg("hi,it's amqp message");
        return "消息已发送";
    }

    @Autowired
    PersonService personService;

    /**
     * 利用redis实现缓存
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/redisCache")
    String redisCache(HttpServletRequest request) {
        String name = request.getParameter("name");
        if (name == null)
            name = "zhangsan";
        return personService.getPersonInfo(name);
    }

    /**
     * 操作redis
     * String Hash List Set ZSet
     * @return
     */
    @RequestMapping(value = "/redisOps")
    void redisOps() {
        personService.redisOps();
    }

    /**
     * 操作mongdo
     */
    @ResponseBody
    @RequestMapping(value = "/mongdo/{ops}")
    String mongdoSave(@PathVariable String ops) {
        try {
            PersonService ps = (PersonService) applicationContext.getBean("personService");
            System.out.println("ops:" + ops);
            Method m = ReflectionUtils.findMethod(ps.getClass(), ops);
            m.invoke(ps);
            return "方法已调用";
        } catch (Exception e) {
            return "未识别的操作";
        }
    }

    @RequestMapping("/aop")
    void aop() {
        personService.aopMethod("liaokailin");
    }

    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
