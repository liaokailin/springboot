package com.lkl.springboot.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.springframework.data.annotation.Id;

@ApiModel("人员信息实体")
public class Person {

    @ApiModelProperty("人员ID")
    @Id
    private String  id;
    @ApiModelProperty("人员名称")
    private String  name;
    @ApiModelProperty("人员年龄")
    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
    }

}
