package com.guangfu.jsc.test;


/**
 * Created by jsc on 2017/12/12.
 */

public class Test2 {
    public static void main(String[] args) {
        B b = new B();
        System.out.println("===========");
        b = new B();

    }

    static class A {
        public String content = "AA";
        public static String text = "父类的Text";

        {
            System.out.println("父类代码块");
        }

        static {
            System.out.println("父类静态代码块");
        }

        A() {
            System.out.println("父类构造方法");
        }

    }

    public static class B extends A {
        public String con = "BB";
        public static String textA = "子类的Text";

        {
            System.out.println("子类代码块");
        }

        static {
            System.out.println("子类静态代码块");
        }

        B() {
            System.out.println("子类构造方法");
        }


    }
}
