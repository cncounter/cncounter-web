package com.cncounter.test.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// 测试能组合成多少个不同的三角形
public class TriAngleArray {
    private static boolean DEBUG = false;

    public static void main(String[] args) {
        int start = 1_0000;
        int multi = 30;
        for (int i = 1; i <= multi; i++) {
            main(start * i);
        }
    }

    public static void main(int loop) {
        long startMillis = System.currentTimeMillis();
        int size = 100;
        List<int[]> list = new ArrayList<>(loop);
        for (int loo = 0; loo < loop; loo++) {
            int[] array = initArray(size, loo);
            list.add(array);
        }
        long prepareMillis = System.currentTimeMillis() - startMillis;
        startMillis = System.currentTimeMillis();
        for (int loo = 0; loo < loop; loo++) {
            int[] array = list.get(loo);
            int count = parseAngle(array);
            if (DEBUG) {
                System.out.println("array=" + Arrays.toString(array));
                System.out.println("result=" + count);
            }
        }
        long timeMillis = System.currentTimeMillis() - startMillis;
        System.out.println("loop=" + loop + ";size=" + size +
                "; timeMillis=" + timeMillis + "; prepareMillis=" + prepareMillis);
    }

    private static int[] initArray(int len, int seed) {
        int[] array = new int[len];
        int start = 1;
        Random r = new Random(seed);
        for (int i = 0; i < len; i++) {
            array[i] = start + r.nextInt(len);
            start = array[i] + 1;
        }
        return array;
    }

    private static int parseAngle(int[] array) {
        int num = 0;
        for (int i = 0; i < array.length; i++) {
            //
            for (int j = i + 1; j < array.length; j++) {
                //
                for (int k = j + 1; k < array.length; k++) {
                    //
                    if (array[i] + array[j] > array[k]) {
                        if (DEBUG) {
                            println("<" + array[i] + "," + array[j] + "," + array[k] + "> ");
                        }
                        num += 1;
                    } else {
                        break;
                    }
                }
            }
        }
        return num;
    }

    public static boolean println(String x) {

        return DEBUG;
    }

}
