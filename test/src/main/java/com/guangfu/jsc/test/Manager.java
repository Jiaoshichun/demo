package com.guangfu.jsc.test;


/**
 * Created by jsc on 2017/12/12.
 */
//静态内部类 使用classloader机制保证线程安全
public class Manager {
    public static void main(String[] args) {
        Anim anim = new Anim();
        System.out.println("anim--->name:" + anim.name);
        System.out.println("anim--->age:" + anim.age);
        anim.eat();
        anim.wan();
        System.out.println("----------------------");
        Anim pig = new Pig();
        System.out.println("pig--->name:" + pig.name);
        System.out.println("pig--->age:" + pig.age);
        pig.eat();
        pig.wan();
        System.out.println("----------------------");
        Anim dog = new Dog();
        System.out.println("dog--->name:" + dog.name);
        System.out.println("dog--->age:" + dog.age);
        dog.eat();
        dog.wan();
        System.out.println("----------------------");
        Dog dog2= (Dog) dog;
        System.out.println("dog--->name:" + dog2.name);
        System.out.println("dog--->age:" + dog2.age);
        dog2.eat();
        dog2.wan();
        dog2.sleep();

    }

    static class Anim {
        public String name = "动物";
        public static int age = 0;

        public void eat() {
            System.out.println("动物的eat方法");
        }

        public static void wan() {
            System.out.println("动物的静态 玩方法");
        }
    }

    static class Pig extends Anim {
        public String name = "小猪";
        public static int age = 5;

        @Override
        public void eat() {
            System.out.println("小猪的eat方法");
        }

        public static void wan() {
            System.out.println("小猪的静态 玩方法");
        }


    }

    static class Dog extends Anim {
        public String name = "小狗";
        public static int age = 3;

        @Override
        public void eat() {
            System.out.println("小狗的eat方法");
        }

        public static void wan() {
            System.out.println("小狗的静态 玩方法");
        }
        public void sleep(){
            System.out.println("小狗的 sleep方法");
        }
    }
}
