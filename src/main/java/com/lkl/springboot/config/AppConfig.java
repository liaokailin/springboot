package com.lkl.springboot.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @ConfigurationProperties 中的 ignoreUnknownFields ＝false时，如果前缀prefix中数据没在属性中申明完整的话，则抛出异常，默认为true
 * 可参考 {@link ServerProperties}设置 tomcat的参数
 * @author lkl
 * @version $Id: AppConfig.java, v 0.1 2015年7月27日 下午10:59:13 lkl Exp $
 */
@Component
@ConfigurationProperties(prefix = "environments", ignoreUnknownFields = true)
public class AppConfig {

    private String     url;

    private String     name;

    private InnerClass innerClass = new InnerClass(); //innerClass 属性名称和类名保持一致

    static class InnerClass {
        String className;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        @Override
        public String toString() {
            return "InnerClass [className=" + className + "]";
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InnerClass getInnerClass() {
        return innerClass;
    }

    public void setInnerClass(InnerClass innerClass) {
        this.innerClass = innerClass;
    }

    @Override
    public String toString() {
        return "AppConfig [url=" + url + ", name=" + name + ", ic=" + innerClass + "]";
    }

}
