package com.guangfu.jsc.test;

public class FastSort {

    public static void main(String[] args) {
        int[] attr = new int[]{12, 2, 55, 11, 3, 10, 65, 79, 6};
        fastSort(attr);
        for (int i : attr) {
            System.out.print(i+"--");
        }

    }
    private static void fastSort(int[] attr) {
        if (attr == null || attr.length < 2) return;
        fast(attr, 0, attr.length - 1);
        short a=1;
    }

    private static void fast(int[] attr, int start, int end) {
        if (start >= end) return;
        int left = start;
        int right = end;
        int value = attr[start];
        while (left < right) {
            while (left < right && attr[right] > value) {
                right--;
            }
            while (left < right && attr[left] <= value) {
                left++;
            }
            if (left < right) {
                int temp = attr[left];
                attr[left] = attr[right];
                attr[right] = temp;
            }
        }
        attr[start] = attr[left];
        attr[left] = value;
        fast(attr, start, left - 1);
        fast(attr, left + 1, end);
    }

}
