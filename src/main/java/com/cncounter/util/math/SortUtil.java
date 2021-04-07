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

    // 分治法: 归并排序
    public static class MergeSort implements IntArraySort {

        // 是否升序: true=升序; false=降序
        private boolean asc;

        public MergeSort(boolean asc) {
            this.asc = asc;
        }

        /**
         * 排序
         *
         * @param array 数组
         */
        @Override
        public void sort(int[] array) {
            mergeSort(array, 0, array.length - 1);
        }

        private void mergeSort(int[] array, int start, int end) {
            // 只有0-1个元素; 不需要排序;
            if (start >= end) {
                return;
            }
            // 拆分为2个部分
            int middle = (start + end) / 2;
            // 分别进行排序
            mergeSort(array, start, middle);
            mergeSort(array, middle + 1, end);
            // 对结果合并
            merge(array, start, middle + 1, end);
        }

        private void merge(final int[] array, final int leftStart, final int rightStart, final int end) {
            // 使用2个临时数组来辅助
            int leftLength = rightStart - leftStart;
            int rightLength = end - rightStart + 1;
            int[] leftHalf = new int[leftLength];
            int[] rightHalf = new int[rightLength];
            // 数组拷贝
            int cur = leftStart;
            for (int i = 0; i < leftHalf.length; i++) {
                leftHalf[i] = array[cur++];
            }
            for (int j = 0; j < rightHalf.length; j++) {
                rightHalf[j] = array[cur++];
            }
            // 依次保存到指定位置
            cur = leftStart;
            int i = 0;
            int j = 0;
            while (i < leftHalf.length && j < rightHalf.length) {
                int pickupNum;
                if (asc == leftHalf[i] < rightHalf[j]) {
                    pickupNum = leftHalf[i++];
                } else {
                    pickupNum = rightHalf[j++];
                }
                array[cur++] = pickupNum;
            }
            // 处理余下的部分
            while (i < leftHalf.length) {
                array[cur++] = leftHalf[i++];
            }
            while (j < rightHalf.length) {
                array[cur++] = rightHalf[j++];
            }

        }
    }

    // 分治法: 快排
    public static class QuickSort implements IntArraySort {

        // 是否升序: true=升序; false=降序
        private boolean asc;
        private Random random = new Random();

        public QuickSort(boolean asc) {
            this.asc = asc;
        }

        /**
         * 排序
         *
         * @param numArray 数组
         */
        @Override
        public void sort(int[] numArray) {
            quickSort(numArray, 0, numArray.length - 1);
        }

        // 快排
        public void quickSort(int[] numArray, int p, int r) {
            if (p >= r) {
                return;
            }
            int pivot = partition(numArray, p, r);
            quickSort(numArray, p, pivot - 1);
            quickSort(numArray, pivot + 1, r);
        }

        // 拆分
        private int partition(int[] numArray, int p, int r) {
            // 选择随机数作为轴
            int pIndex = p + random.nextInt(r - p);
            swap(numArray, pIndex, r);
            //
            int q = p;
            for (int j = p; j < r; j++) {
                if (asc == (numArray[j] <= numArray[r])) {
                    swap(numArray, j, q++);
                }
            }
            // 最后交换轴点的值
            swap(numArray, q, r);
            return q;
        }

        private static void swap(int[] numArray, int index1, int index2) {
            int temp = numArray[index1];
            numArray[index1] = numArray[index2];
            numArray[index2] = temp;
        }
    }

    public static void main(String[] args) {
        testSelectionSort();
        testInsertionSort();
        testMergeSort();
        testQuickSort();
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

    public static void testMergeSort() {
        // 10w数据量: 66ms; 100w数据量: 500ms
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        boolean asc = true;
        IntArraySort impl = new MergeSort(asc);
        testIntArraySort(impl, asc);
        impl = new MergeSort(!asc);
        testIntArraySort(impl, !asc);
        //
        stopwatch.stop();
        System.out.println("测试通过:" +
                impl.getClass().getSimpleName()
                + "; 耗时ms:" + stopwatch.getTotalTimeMillis());

    }


    public static void testQuickSort() {
        // 10w数据量: 38ms; 100w数据量: 309ms
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        boolean asc = true;
        IntArraySort impl = new QuickSort(asc);
        testIntArraySort(impl, asc);
        impl = new QuickSort(!asc);
        testIntArraySort(impl, !asc);
        //
        stopwatch.stop();
        System.out.println("测试通过:" +
                impl.getClass().getSimpleName()
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
