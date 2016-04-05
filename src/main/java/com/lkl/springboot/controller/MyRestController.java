package com.lkl.springboot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lkl.springboot.domain.Person;
import com.lkl.springboot.exception.RestException;
import com.lkl.springboot.metric.CusMetric;
import com.lkl.springboot.response.SpringBootResponse;
import com.lkl.springboot.swagger.SwaggerConfig;

/**
 * RequestMethod 对应REST操作
 * PUT    : 修改
 * GET    : 获取
 * POST   : 新增
 * DELETE : 删除
 * 
 * @author lkl
 * @version $Id: MyRestController.java, v 0.1 2015年8月4日 下午5:28:39 lkl Exp $
 */
@RestController
@RequestMapping(value = "/" + SwaggerConfig.PROJECT_NAME + "/api/" + SwaggerConfig.PROJECT_VERSION + "/person", produces = MediaType.APPLICATION_JSON_VALUE)
@Api("人员api")
public class MyRestController {

    /**
     * 存在@ApiImplicitParams 配置多个参数
     * @ApiImplicitParam
     * paramType :  path, query, body, header 
     * name：参数名
     * value: 参数简要描述
     * dataType：数据类型
     * defaultValue: 默认值
     * @param name
     * @return
     */
    @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", dataType = "String", defaultValue = "helloworld")
    /**
     * @ApiOperation: 该操作说明
     * value: 操作说明
     * notes：其他说明
     */
    @ApiOperation(value = "根据用户名称查询人员信息", notes = "需要传递name参数")
    /**
     * @ApiResponses: 操作返回类型，是数组，依据不同的http status code 会有不同的返回
     * 
     */
    @ApiResponses(value = { @ApiResponse(code = 400, message = "错误的请求", response = SpringBootResponse.class),
            @ApiResponse(code = 401, message = "请求未通过认证", response = SpringBootResponse.class) })
    /**
     * @RequestMapping spring的配置
     * 
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Person getPersonByName(@PathVariable("name") String name) {
        if ("zhangsan".equals(name)) {
            throw new RestException(HttpStatus.UNAUTHORIZED.value() + ";" + "哈哈 ,错了");
        }
        Person p = new Person();
        p.setAge(25);
        p.setName(name);
        p.setId(UUID.randomUUID().toString());
        return p;
    }

    @ApiOperation(value = "创建新人员", notes = "传递人员信息", nickname = "create new person", response = SpringBootResponse.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public SpringBootResponse savePerson(@RequestBody Person person) {
        SpringBootResponse r = new SpringBootResponse();
        System.out.println(person);
        if (person.getName().equals("zhangsan")) {
            throw new RestException(HttpStatus.UNAUTHORIZED.value() + ";" + "错误");
        }
        r.setCode(HttpStatus.CREATED.value());
        r.setMsg("创建成功");
        return r;
    }

    @ApiOperation(value = "按照Id修改人员信息", notes = "传递人员信息", nickname = "update person info", response = SpringBootResponse.class)
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public SpringBootResponse updatePersonById(@RequestBody Person person, @PathVariable("id") String id) {
        SpringBootResponse r = new SpringBootResponse();
        System.out.println("id:" + id);
        System.out.println(person);
        if (person.getName().equals("zhangsan")) {
            throw new RestException(HttpStatus.UNAUTHORIZED.value() + ";" + "错误");
        }
        r.setCode(HttpStatus.OK.value());
        r.setMsg("修改成功");
        return r;
    }

    @Autowired
    CusMetric cusMetric;

    @ApiOperation(value = "测试metric自增", notes = "测试metric自增")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void login() {
        cusMetric.doLogin();

    }

    @ApiOperation(value = "Retrofit-tet", notes = "Retrofit-tet", nickname = "update person info", response = SpringBootResponse.class)
    @RequestMapping(value = "/retrofit/test/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String testRetrofit(@PathVariable("id") String id) {
        System.out.println("id:" + id);
        return "hello" + id;
    }

}
