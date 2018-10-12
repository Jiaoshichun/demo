package com.guangfu.jsc.test;

import java.lang.reflect.Field;

public class Demo {

    public Demo(int a) {

    }

    public static void main(String[] args) {

        try {
            Field field = Demo.class.getField("");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

    public <S> S test(S t) {
        return t;
    }


    public int test2(@Deprecated int a, int b) {

        return 1;
    }

}
