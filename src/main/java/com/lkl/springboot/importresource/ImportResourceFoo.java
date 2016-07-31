package com.lkl.springboot.importresource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lkl.springboot.domain.Person;
import com.lkl.springboot.domain.User;

/**
 * 
 * @ImportResource 导入配置文件 建议使用@ComponentScan
 * @author lkl
 * @version $Id: ImportResourceFoo.java, v 0.1 2015年7月27日 下午4:09:59 lkl Exp $
 */
//@Configuration
//@ImportResource(value = "xml_config.xml")
//@ComponentScan("com.u51.lkl.springboot")
public class ImportResourceFoo {

    public static void main1(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ImportResourceFoo.class);
        Person p = (Person) ctx.getBean("person");
        System.out.println(p); //Person [name=zhangsan, age=12]
        User u = (User) ctx.getBean("user");
        System.out.println(u.getUserName());
    }
}
