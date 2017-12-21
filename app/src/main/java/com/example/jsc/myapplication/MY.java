package com.example.jsc.myapplication;


import android.os.Build;
import android.support.annotation.RequiresApi;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jsc on 2017/6/28.
 */

public class MY {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) {
        test2();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void test1() {
        List<String> strings = Arrays.asList("11", "22", "33", "44");
        strings.stream().map((s) -> Integer.valueOf(s) + 11).filter((s) -> s != 33).
                flatMap((s) -> Stream.of(s + "hello", s + "world")).forEach(System.out::println);
        System.out.println(strings);
        Optional<Object> o = Optional.ofNullable(null);
        System.out.print(o.orElseGet(() -> 1));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void test2() {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            integers.add(i);
        }
        List<Integer> collect = integers.stream().limit(10).skip(1).collect(Collectors.toList());
        System.out.print(collect);
    }
}
