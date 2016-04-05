package com.lkl.springboot.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("返回结果集")
public class SpringBootResponse {

    @ApiModelProperty("结果码")
    private int    code;
    @ApiModelProperty("处理消息")
    private String msg;

    public SpringBootResponse(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public SpringBootResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
