package com.example.jsc.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jsc on 2017/12/12.
 */

public class Test2 {
    public static void main(String[] args) throws JSONException {
        B b = new B();
        System.out.print(b.content);

    }

    static abstract class A {
        public String content = "AA";

        A() {
            init();
        }

        abstract void init();

    }

    public static class B extends A {

        @Override
        void init() {
            content = "初始化了";
        }

    }
}
