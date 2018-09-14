package com.guangfu.jsc.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by jsc on 2017/8/3.
 */

public class Test {
    public static void main(String[] args) {
        int[] ints = new int[2];
        ints[0] = 10;
        ints[1] = 1;
        int[] ints1 = new int[5];
        ints1[0] = 1;
        ints1[1] = 10;
        ints1[2] = 2;
        ints1[3] = 4;
        ints1[4] = 1;
        differentElement(ints, ints1);
    }

    public static void differentElement(int[] attrs1, int[] attrs2) {
        StringBuilder stringBuilder = new StringBuilder();//由于输出字符串  使用StringBuilder可变字符串 进行字符串的接收
        if (attrs1 != null || attrs2 != null) {//此处是为了防止两个数组为null的情况  只要有一个不为null就向下进行
            if (attrs1 == null) {//如果数组1为null  则数组2则肯定不为空  数组2都是不同的元素
                for (int i = 0; i < attrs2.length; i++) {
                    stringBuilder.append(attrs2[i]);
                }
            } else if (attrs2 == null) {//如果数组2为null  则数组1则肯定不为空  数组1都是不同的元素
                for (int i = 0; i < attrs1.length; i++) {
                    stringBuilder.append(attrs1[i]);
                }
            } else {//正常情况下
                HashSet<Integer> set1 = new HashSet<>();//将数组转为set集合 HashSet的特性：元素不可重复   这样可以去掉数组内本身相同的元素
                for (int i = 0; i < attrs1.length; i++) {
                    set1.add(attrs1[i]);
                }
                HashSet<Integer> set2 = new HashSet<>();
                for (int i = 0; i < attrs2.length; i++) {//将数组转为set集合 HashSet的特性：元素不可重复 这样可以去掉数组内本身相同的元素
                    set2.add(attrs2[i]);
                }
                //遍历其中一个set集合 向另个set集合中添加   利用HashSet添加相同元素会失败的特性
                Iterator<Integer> iterator1 = set1.iterator();
                while (iterator1.hasNext()) {
                    Integer next = iterator1.next();
                    if (!set2.add(next)) {
                        set2.remove(next);//如果添加失败 则表示该元素重复 将该元素删除  如果成功 则表示该元素是不相同元素 不做处理
                    }
                }

                //最后set2中的元素 即为不相同元素 将set2中的元素遍历取出 StringBuilder中
                Iterator<Integer> finalIterator = set2.iterator();
                while (finalIterator.hasNext()) {
                    stringBuilder.append(finalIterator.next());
                }
            }
        }
        if (stringBuilder.length() == 0) {
            System.out.println("都相同");
        } else {
            System.out.println("长度为:" + stringBuilder.length() + "--不同的为:" + stringBuilder.toString());
        }
    }
    public static void differentElement2(int[] attrs1, int[] attrs2) {
        int[] ints = new int[20];
        int count=0;
        for (int i = 0; i < attrs1.length; i++) {
            int a1 = attrs1[i];
            boolean equals=false;
            for (int j = 0; j < attrs2.length; j++) {
                int a2 = attrs2[j];
                if(a1==a2){
                    equals=true;
                    break;
                }
            }
            if(false){

            }

        }
    }
}
