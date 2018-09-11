package com.example.lib;

import java.lang.instrument.Instrumentation;

public class MySizeOf {
    static Instrumentation instrumentation;

    // 第一个参数由 –javaagent， 第二个参数由 JVM 传入
    public static void premain(String agentArgs, Instrumentation instP) {
        instrumentation = instP;
    }

    // 返回没有子类对象大小的大小
    public static long sizeOf(Object o) {
        if (instrumentation == null) {
            throw new IllegalStateException("Can not access instrumentation environment.\n" +
                    "Please check if jar file containing SizeOfAgent class is \n" +
                    "specified in the java's \"-javaagent\" command line argument.");
        }
        return instrumentation.getObjectSize(o);
    }



}
