package com.guangfu.jsc.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jsc on 2017/10/30.
 */

public class Test8 {
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Filters {
        Filter[] value();
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Filters.class)
    public @interface Filter {
        String value();
    }

    @Filter("fliter1")
    @Filter("fliter2")
    public class F {

    }

    public static void main(String[] args) {
        repeatable();
        optional();

        ReentrantLock lock = new ReentrantLock();

    }

    //重复注解练习
    private static void repeatable() {
        Filter[] annotationsByType = F.class.getAnnotationsByType(Filter.class);
        for (Filter f : annotationsByType) {
            System.out.println(f.value());
        }
    }

    private static void optional() {
        Optional<String> o = Optional.ofNullable(null);
        System.out.print(o.orElse("空的"));
        System.out.print(o.orElseGet(() -> "11"));

    }
}
