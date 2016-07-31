package com.lkl.springboot.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.springframework.asm.ClassReader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;

/**
 * 测试asm加载Class，在扫描BeanDefinition时获取class信息
 * 
 * @author liaokailin
 * @version $Id: LoadAnnotationMetadata.java, v 0.1 2016年7月29日 下午2:03:32 liaokailin Exp $
 */
public class LoadAnnotationMetadata {

    /**
     * App.class
     * public class App {
        public static void main(String[] args) {
            System.out.println("Hello World!");
        }
    
        @Deprecated
        public void hello() {
    
        }
        }
     * 
     * @throws IOException
     */
    @Test
    public void testAsm() throws IOException {
        InputStream is = new FileInputStream(new File(
            "/Users/liaokailin/code/github/jvm/target/classes/com/lkl/services/jvm/App.class"));
        ClassReader classReader = new ClassReader(is); //使用asm处理扫描的Class文件
        ClassLoader classLoader = LoadAnnotationMetadata.class.getClassLoader();
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor(classLoader);
        classReader.accept(visitor, ClassReader.SKIP_DEBUG);
        ClassMetadata classMetadata = visitor;
        AnnotationMetadata annotationMetadata = visitor;
        System.out.println(classMetadata.getClassName());
        System.out.println(annotationMetadata.hasAnnotatedMethods("java.lang.Deprecated"));
    }
}
