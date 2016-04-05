package com.lkl.springboot.endpoint;

import java.util.List;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

/**
 * 自定义端点
 * 
 * @author lkl
 * @version $Id: CusEndPoint.java, v 0.1 2015年8月12日 下午8:10:16 lkl Exp $
 */
@Component
public class CusEndPoint implements Endpoint<List<String>> {

    /**
     *  端点id
     */
    @Override
    public String getId() {
        return "person";
    }

    /**
     * 是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     *  是否敏感数据
     */
    @Override
    public boolean isSensitive() {
        return false;
    }

    /**
     * 返回该端点结果集
     * @see org.springframework.boot.actuate.endpoint.Endpoint#invoke()
     */
    @Override
    public List<String> invoke() {
        List<String> list = Lists.newArrayList("liaokailin", "25");
        return list;
    }

}
