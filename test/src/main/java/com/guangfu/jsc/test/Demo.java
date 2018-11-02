package com.guangfu.jsc.test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Demo {

    public static void main(String[] args) {
        String[] names = {"a", "b", "c"};
        ArrayList<Runnable> runnables = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            String n = names[i];
            runnables.add(() -> System.out.println(n));
        }
        runnables.forEach(runnable -> new Thread(runnable).start());
    }

    interface Collection2<T> extends Collection<T> {
        default void forEachIf(Consumer<T> action, Predicate<T> filter) {
            forEach(t -> {
                if (filter.test(t)) action.accept(t);
            });
        }
    }

}
