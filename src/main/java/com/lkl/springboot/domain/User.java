package com.lkl.springboot.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@PropertySource("application.properties")
@Component
@JsonIgnoreProperties({ "age" })
@JsonSerialize()
@JsonInclude(Include.NON_NULL)
//不在推荐 @JsonSerialize(include = Inclusion.NON_NULL) 使用@JsonInclude(Include.NON_NULL)
public class User {

    //  @Value("${hit:liaokailin}")
    //调用  application.yml中的配置，内部通过YamlPropertySourceLoader加载信息
    @Value("${application.name}")
    @JsonProperty("name")
    @JsonPropertyOrder({ "1" })
    private String  userName;
    @Value("${my.number:25}")
    private Integer age;
    private String  remark;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date    birthday;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User [userName=" + userName + ", age=" + age + "]";
    }

}
