package com.u51.lkl.springboot.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;

 
public class StrSubstitutorTest {
    private static Map<String, Object> map = new HashMap<String, Object>();
    static {
        for (int i = 0; i < 10; i++) {
            map.put(i + "", "test-" + i);
        }
    }

    public static void main(String[] args) {

        String test = "The ${1} jumped over the ${2}";
        StrSubstitutor str = new StrSubstitutor(new StrLookup() {

            @Override
            public String lookup(String key) {
                String prop = getProperty(key);
                return (prop != null) ? prop : null;
            }

            private String getProperty(String key) {
                return map.get(key).toString();
            }

        });
        System.out.println(str.replace(test)); // 结果： The test-1 jumped over the test-2 
        ;

    }

}
