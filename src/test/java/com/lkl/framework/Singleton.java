package com.lkl.framework;

/**
 * 单例，考虑多线程
 * @author lkl
 *
 */
public class Singleton {

    private Singleton() {
        System.out.println("---construct----");
    }

    /**
     * 类级内部类相当于其外部类的成员，只有在第一次被使用的时候才被会装载
     */
    static class SingletonHolder {
        private static Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }

    public static String getInstance1() {
        return "123";
    }

    public static void main(String[] args) {
        System.out.println(getInstance1());
    }

}
