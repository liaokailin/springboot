package com.u51.lkl.springboot.test.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.StandardAnnotationMetadata;

import com.lkl.springboot.Application;

public class ParseAnnotation {
    public static void main(String[] args) {
        StandardAnnotationMetadata meta = new StandardAnnotationMetadata(Application.class, true);
        //    AnnotationAttributes componentScan = AnnotationConfigUtils.attributesFor(meta, ComponentScan.class);
        AnnotationAttributes s = AnnotationAttributes.fromMap(meta.getAnnotationAttributes(
            ComponentScan.class.getName(), false));
        meta.getMetaAnnotationTypes("xxx");//获取执行注解的方法
        System.out.println(s);
        System.out.println(meta.getAnnotationTypes());
    }
}
