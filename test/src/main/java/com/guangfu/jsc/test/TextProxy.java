package com.guangfu.jsc.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TextProxy {
    public static void main(String[] args) {
        //1. 创建目标对象
        RealSubject realSubject = new RealSubject();
        //2.创建代理对象  第一个参数，必须是目标对象的classloader 第二个参数 必须是目标对象的接口  第三个参数 动态代理处理逻辑  返回值 必须是主题接口。
        Subject subject = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[]{Subject.class}, new CustomHandler(realSubject));
        //获取代理对象第二种方式
//        Class<?> proxyClass = Proxy.getProxyClass(RealSubject.class.getClassLoader(), RealSubject.class.getInterfaces());
//        try {
//            Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
//            subject = (Subject) constructor.newInstance(new CustomHandler(realSubject));
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        //3.调用主题接口的方法 该方法实际是通过动态代理类间接调用目标对象的方法
        subject.say();
        System.out.println("------------------");
        System.out.println(subject.wan());
    }

    interface Subject {
        void say();

        String wan();
    }

    static class RealSubject implements Subject {

        @Override
        public void say() {
            System.out.println("说点啥呢");
        }

        @Override
        public String wan() {
            System.out.println("动态代理原来这么玩的");
            return "玩完";
        }
    }

    static class CustomHandler implements InvocationHandler {
        private Subject subject;

        public CustomHandler(Subject subject) {
            this.subject = subject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            Object invoke = null;
            switch (method.getName()) {
                case "wan":
                    System.out.println("玩前");
//                    invoke = method.invoke(subject, args);
                    break;
                case "say":
                    System.out.println("say前");
//                    invoke = method.invoke(subject, args);
                    break;
            }
            System.out.println("结束");
            return null;
        }
    }
}
