package com.lkl.springboot.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import com.google.common.collect.Lists;
import com.lkl.springboot.domain.Person;

/**
 * 排序测试
 * {@link Comparable} 实现去比较
 * {@link Comparator} 
 * @author lkl
 * @version $Id: MyTestSort.java, v 0.1 2015年8月7日 下午5:04:16 lkl Exp $
 */
public class MyTestSort {

    public static void main(String[] args) {
        List instances = new ArrayList();
        AnnotationAwareOrderComparator.sort(instances); //观察源码
    }

    public static void testMethod() {

        List<Person> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Person p = new Person();
            p.setAge(i + 1);
            p.setName("liaokailin" + i);
            list.add(p);
        }
        Collections.sort(list, new Comparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                if (o1.getAge() > o2.getAge()) {
                    return -1;
                } else if (o1.getAge() < o2.getAge()) {
                    return 1;
                }
                return 0;
            }

        });
        for (Person p : list) {
            System.out.println(p);
        }

    }

}
