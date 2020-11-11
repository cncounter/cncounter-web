package com.cncounter.test.util;

import org.junit.Test;

public class CollectionUtilsTest {

    @Test
    public void test() {
        int[] demoArray = demoArray(10);
        Integer[] demoIntegerArray = demoIntegerArray(10);
        //
        testCast(demoIntegerArray);
    }

    private void testCast(Integer[] demoIntegerArray) {
        Object[] objArray = (Object[])demoIntegerArray;
        Number[] numArray = (Number[])demoIntegerArray;
        // class [Ljava.lang.Integer;
        System.out.println(objArray.getClass());
        // [Ljava.lang.Integer;@26be92ad
        System.out.println(objArray);
    }


    public static int[] demoArray(int len) {
        int[] demoArray = new int[len];
        for (int i = 0; i < demoArray.length; i++) {
            demoArray[i] = i * 11;
        }
        System.out.println(demoArray); // [I@1d057a39
        System.out.println(demoArray.getClass()); // class [I
        return demoArray;
    }


    public static Integer[] demoIntegerArray(int len) {
        Integer[] demoArray = new Integer[len];
        for (int i = 0; i < demoArray.length; i++) {
            demoArray[i] = i * 11;
        }
        // [Ljava.lang.Integer;@26be92ad
        System.out.println(demoArray);
        // class [Ljava.lang.Integer;
        System.out.println(demoArray.getClass());
        return demoArray;
    }
}
