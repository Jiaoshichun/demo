package com.guangfu.jsc.test;

import com.sun.org.apache.xpath.internal.compiler.FunctionTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by jsc on 2017/10/20.
 */

public class Text {
    private HashMap<String, Integer> hashMap;

    public static void main(String[] args) {
//        testCallable();
//        RandomTest();
        BufferTest();

//        try {
//            Field field = Text.class.getDeclaredField("hashMap");
//            System.out.println("field.getType():" + field.getType());
//            Type genericType = field.getGenericType();
//            System.out.println("genericType.getTypeName():" + genericType.getTypeName());
//            if (genericType instanceof ParameterizedType) {
//                ParameterizedType parameterizedType = (ParameterizedType) genericType;
//                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//                for (int i = 0; i < actualTypeArguments.length; i++) {
//                    System.out.println("第" + i + "个泛型为:" + actualTypeArguments[i].getTypeName());
//                }
//            }
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
    }

    public static void testCallable() {
        CallableThreadTest ctt = new CallableThreadTest();
        FutureTask<Integer> ft = new FutureTask<>(ctt);
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量i的值" + i);
            if (i == 20) {
                new Thread(ft, "有返回值的线程").start();
            }
        }
        try {
            System.out.println("子线程的返回值：" + ft.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public static class CallableThreadTest implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int i = 0;
            for (; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            return i;
        }
    }

    public static void RandomTest() {
        try {
            RandomAccessFile rw = new RandomAccessFile(new File("e:\\test.txt"), "rw");
            rw.write("写入文字12345678".getBytes());
            rw.seek(30);
            rw.write("在三个位置开始写".getBytes());
            rw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void BufferTest() {
        try {
            RandomAccessFile rw = new RandomAccessFile(new File("e:\\test.txt"), "rw");
            FileChannel channel = rw.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(48);
            int read = channel.read(buffer);
            while (read != -1) {
                System.out.println("read:" + read);
                buffer.flip();//切换读读的模式

                while (buffer.hasRemaining()) {
                    System.out.println("读取的内容为:" + buffer.getChar());
                }
                buffer.clear();
                read = channel.read(buffer);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
