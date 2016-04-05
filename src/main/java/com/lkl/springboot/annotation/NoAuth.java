package com.lkl.springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 无需授权
 * 
 * @author lkl
 * @version $Id: NoAuth.java, v 0.1 2015年7月29日 下午11:15:11 lkl Exp $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoAuth {

}
