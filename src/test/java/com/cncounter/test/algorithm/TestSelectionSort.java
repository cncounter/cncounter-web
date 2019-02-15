package com.cncounter.test.algorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 最近在看《算法图解》;
 * 测试-选择排序:  每次选择最小/最大的一个数字;
 * 只处理 int[];
 * Java中- Arrays提供了对应的方法;
 *
 * 选择排序的时间复杂度(N为规模, c为常数因子):
 * = N +(N-1) + (N-2) +...+1
 * = c * (N*(N+1))/2
 * = O(N^2)
 */
public class TestSelectionSort {
    public static void main(String[] args) {
        //
        for (int i = 0; i < 40; i++) {
            test();
        }
    }

    public static void test() {
        //
        int length = 20;
        boolean descFlag = false;
        // 基准数组
        int[] baseArray = generateSourceArray(length, 100 * length);

        //
        int[] sourceArray = Arrays.copyOf(baseArray, baseArray.length);
        // 排序前
        System.out.println("===========-----------------============");
        System.out.println("BB=====" + Arrays.toString(sourceArray));
        // 排序
        int findTimes =selectionSort(sourceArray, descFlag);
        // 排序后
        System.out.println("AA=====" + Arrays.toString(sourceArray));
        System.out.println("findTimes=" + findTimes);
        // 此处, 可以实现一个自动比较的方法; assert(sourceArray, descFlag);

        // 使用JDK自带的排序方法
        // 对比数组
        int[] compareArray = Arrays.copyOf(baseArray, baseArray.length);
        Arrays.sort(compareArray, 0, compareArray.length);
        // 排序后
        System.out.println("SS=====" + Arrays.toString(sourceArray));
        // 比对是否相等
        System.out.println("AS:::::" + Arrays.equals(sourceArray, compareArray));
    }

    private static int[] generateSourceArray(int length, int bound) {
        int[] sourceArray = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sourceArray[i] = random.nextInt(bound);
        }
        return sourceArray;
    }

    // 单线程计数器
    public static AtomicInteger counter = new AtomicInteger(0);
    /**
     * @param sourceArray 源数组
     * @param descFlag    是否降序
     * @return
     */
    public static int selectionSort(int[] sourceArray, boolean descFlag) {
        // 防御式编程
        if (null == sourceArray || sourceArray.length < 2) {
            return 0;
        }
        counter.set(0);
        // 数组长度
        int sourceLength = sourceArray.length;
        // 双层循环
        for (int i = 0; i < sourceLength; i++) {
            // 找出目标值
            int theIndex = findTheValueIndex(sourceArray, i, descFlag);
            // 交换2个索引处的值
            swapArrayValue(sourceArray, i, theIndex);
        }
        return counter.getAndSet(0);
    }

    // 找出目标值
    private static int findTheValueIndex(int[] sourceArray, int startIndex, boolean descFlag) {
        if (null == sourceArray || sourceArray.length < 1) {
            return -1;
        }
        // 数组长度
        int sourceLength = sourceArray.length;
        if (startIndex < 0 || startIndex + 1 > sourceLength) {
            return -1;
        }
        // 根据方向, 确定目标值的索引;
        int theIndex = startIndex;
        int theValue = sourceArray[theIndex];
        //
        for (int k = startIndex + 1; k < sourceLength; k++) {
            //
            int curValue = sourceArray[k];
            // 此处其实也可以封装另外一个compare(v1, v2, dir)方法
            if (descFlag) { // 倒序
                if (curValue > theValue) {
                    theIndex = k;
                    theValue = sourceArray[theIndex];
                }
            } else {
                if (curValue < theValue) {
                    theIndex = k;
                    theValue = sourceArray[theIndex];
                }
            }
            counter.incrementAndGet();
        }
        //
        return theIndex;
    }

    // 交换2个索引处的值
    private static void swapArrayValue(int[] sourceArray, int baseIndex, int theIndex) {
        // 防御编程;
        if (null == sourceArray || sourceArray.length < 1) {
            return;
        }
        if (baseIndex == theIndex) {
            return;
        }
        // 数组长度
        int sourceLength = sourceArray.length;
        if (baseIndex < 0 || baseIndex + 1 > sourceLength) {
            return;
        }
        if (theIndex < 0 || theIndex + 1 > sourceLength) {
            return;
        }
        // 交换数值
        int baseTemp = sourceArray[baseIndex];
        sourceArray[baseIndex] = sourceArray[theIndex];
        sourceArray[theIndex] = baseTemp;
    }

}
