package com.cncounter.util.math;

import org.springframework.util.StopWatch;

import java.util.Random;

/**
 * 算法工具
 */
public class SearchAlgorithmUtil {

    public static interface IntArraySearch {
        /**
         * 查找
         *
         * @param numArray    数组
         * @param searchValue 查找值
         * @return 返回在数组中的index, 不存在则返回 -1
         */
        public int search(int[] numArray, final int searchValue);
    }

    public static class BinarySearchAsc implements IntArraySearch {
        @Override
        public int search(int[] numArray, final int searchValue) {
            int targetIndex = -1;
            int fromIndex = 0;
            int toIndex = numArray.length - 1;
            while (fromIndex <= toIndex) {
                int guessIndex = (fromIndex + toIndex) / 2;
                int itemValue = numArray[guessIndex];
                if (searchValue == itemValue) {
                    targetIndex = guessIndex;
                    break;
                } else if (fromIndex >= toIndex) {
                    break; // 没有找到
                } else if (itemValue < searchValue) {
                    fromIndex = guessIndex + 1;
                } else if (itemValue > searchValue) {
                    toIndex = guessIndex - 1;
                }
            }

            return targetIndex;
        }
    }

    public static class LinearSearchAsc implements IntArraySearch {
        @Override
        public int search(int[] numArray, final int searchValue) {
            int targetIndex = -1;
            for (int i = 0; i < numArray.length; i++) {
                int v = numArray[i];
                if (v == searchValue) {
                    targetIndex = i;
                    break;
                }
            }
            return targetIndex;
        }
    }


    public static void main(String[] args) {
        // 10w数据量: 5521ms; 100w数据量: 961640ms
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        IntArraySearch linearSearch = new LinearSearchAsc();
        testIntArraySearch(linearSearch);
        stopwatch.stop();
        System.out.println("测试通过:" + linearSearch.getClass().getSimpleName() + "; 耗时ms:" + stopwatch.getTotalTimeMillis());
        // 10w数据量: 26ms; 100w数据量: 274ms
        stopwatch = new StopWatch();
        stopwatch.start();
        IntArraySearch binarySearch = new BinarySearchAsc();
        testIntArraySearch(binarySearch);
        stopwatch.stop();
        System.out.println("测试通过:" + binarySearch.getClass().getSimpleName() + "; 耗时ms:" + stopwatch.getTotalTimeMillis());
    }

    public static void testIntArraySearch(IntArraySearch impl) {
        int[] numArray = initIntArray(100001);
        int searchStart = 0;
        int searchMax = numArray[numArray.length - 1] + 5;
        int hitSize = 0;
        for (int i = searchStart; i < searchMax; i++) {
            int searchValue = i;
            int targetIndex = impl.search(numArray, searchValue);
            if (targetIndex < 0) {
                continue;
            }
            int targetValue = numArray[targetIndex];
            if (searchValue != targetValue) {
                String errorMessage = "查找值与目标值不一致:searchValue=" + searchValue + ";targetIndex=" + targetIndex + ";targetValue=" + targetValue;
                throw new RuntimeException(errorMessage);
            }
            hitSize++;
        }
        if (numArray.length != hitSize) {
            String errorMessage = "命中数与数组长度不一致:hitSize=" + hitSize + "; numArray.length=" + numArray.length;
            throw new RuntimeException(errorMessage);
        }
    }

    private static int[] initIntArray(int size) {
        int[] numArray = new int[size];
        int seed = size / 2;
        Random random = new Random(seed);
        int startNum = 0;
        for (int i = 0; i < numArray.length; i++) {
            startNum += 1;
            startNum += random.nextInt(5);
            numArray[i] = startNum;
        }
        return numArray;
    }
}
