package com.cncounter.util.math;

import org.springframework.util.StopWatch;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 排序工具
 */
public class SortUtil {

    public static interface IntArraySort {
        /**
         * 排序
         *
         * @param numArray 数组
         */
        public void sort(int[] numArray);
    }


    // 插入排序
    public static class InsertionSort implements IntArraySort {

        // 是否升序: true=升序; false=降序
        private boolean asc;

        public InsertionSort(boolean asc) {
            this.asc = asc;
        }

        /**
         * 排序
         *
         * @param numArray 数组
         */
        @Override
        public void sort(int[] numArray) {
            for (int i = 1; i < numArray.length; i++) {
                insert(numArray, i - 1, numArray[i], asc);
            }
        }

        private static void insert(int[] numArray, int rightIndex,
                                   int value, boolean asc) {
            int j = rightIndex;
            for (; j >= 0 && (asc == (numArray[j] > value)); j--) {
                numArray[j + 1] = numArray[j];
            }
            numArray[j + 1] = value;
        }
    }

    // 选择排序
    public static class SelectionSort implements IntArraySort {

        // 是否升序: true=升序; false=降序
        private boolean asc;

        public SelectionSort(boolean asc) {
            this.asc = asc;
        }

        /**
         * 排序
         *
         * @param numArray 数组
         */
        @Override
        public void sort(int[] numArray) {
            for (int i = 0; i < numArray.length; i++) {
                int itemValue = numArray[i];
                int targetIndex = findIndex(numArray, i, asc);
                int targetValue = numArray[targetIndex];
                if (asc == (targetValue < itemValue)) {
                    swap(numArray, i, targetIndex);
                }
            }
        }

        private static int findIndex(int[] numArray, final int startIndex, boolean asc) {
            int targetIndex = startIndex;
            int targetValue = numArray[targetIndex];
            for (int i = targetIndex + 1; i < numArray.length; i++) {
                int itemValue = numArray[i];
                if (asc == (itemValue < targetValue)) {
                    targetIndex = i;
                    targetValue = itemValue;
                }
            }
            return targetIndex;
        }

        // 交换
        private static void swap(int[] numArray, int index1, int index2) {
            if (index1 == index2) {
                return;
            }
            int temp = numArray[index1];
            numArray[index1] = numArray[index2];
            numArray[index2] = temp;
        }
    }


    public static void main(String[] args) {
        testSelectionSort();
        testInsertionSort();
    }

    public static void testSelectionSort() {
        // 10w数据量: 5521ms; 100w数据量: 961640ms
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        boolean asc = true;
        IntArraySort selectionSort = new SelectionSort(asc);
        testIntArraySort(selectionSort, asc);
        //
        selectionSort = new SelectionSort(!asc);
        testIntArraySort(selectionSort, !asc);
        stopwatch.stop();
        System.out.println("测试通过:" +
                selectionSort.getClass().getSimpleName()
                + "; 耗时ms:" + stopwatch.getTotalTimeMillis());
    }

    public static void testInsertionSort() {
        // 10w数据量: 5521ms; 100w数据量: 961640ms
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        boolean asc = true;
        IntArraySort insertionSort = new InsertionSort(asc);
        testIntArraySort(insertionSort, asc);
        //
        insertionSort = new InsertionSort(!asc);
        testIntArraySort(insertionSort, !asc);
        stopwatch.stop();
        System.out.println("测试通过:" +
                insertionSort.getClass().getSimpleName()
                + "; 耗时ms:" + stopwatch.getTotalTimeMillis());
    }

    public static void testIntArraySort(IntArraySort impl, boolean asc) {
        int[] numArray = initIntArray(100001);
        impl.sort(numArray);
        int preValue = numArray[0];
        for (int i = 1; i < numArray.length; i++) {
            int itemValue = numArray[i];
            if ((preValue == itemValue)) {
                //
            } else if (asc == (preValue > itemValue)) {
                throw new IllegalStateException("IllegalState: i=" + i + ";value=" + itemValue + ";preValue=" + preValue);
            }
            preValue = itemValue;
        }
    }

    private static int[] initIntArray(int size) {
        int[] numArray = new int[size];
        int seed = size / 2;
        Random random = new Random(seed);
        for (int i = 0; i < numArray.length; i++) {
            numArray[i] = random.nextInt(size * 5);
        }
        return numArray;
    }
}
