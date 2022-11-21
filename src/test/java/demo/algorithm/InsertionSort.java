package demo.algorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

// 插入排序算法
// 原则上尽量避免中间变量: 但在Java中, 栈内存和堆内存是2块不同的空间
public class InsertionSort {
    // 排序: 默认使用升序
    public static void sort(int[] array) {
        // 防御式编程: 判空
        if (null == array) {
            return;
        }
        // 数组只有0个或者1个元素: 不需要排序
        if (array.length <= 1) {
            return;
        }
        // 从下标1开始遍历, 外层遍历: 从前往后
        for (int i = 1; i < array.length; i++) {
            // 当前索引位置: 初始为 i
            int curIndex = i;
            // 依次和前面的元素比较
            while (array[curIndex] < array[curIndex - 1]) {
                // 交换元素
                swap(array, curIndex, curIndex - 1);
                // 内层循环: 从后往前
                curIndex--;
                if (curIndex <= 0) {
                    // 内层循环的第2种退出条件;
                    // 第1种退出条件是while自带的判断条件
                    break;
                }
            } // end of while
        } // end of for
    }

    // 交换元素
    private static void swap(int[] array, int sourceIndex, int targetIndex) {
        //log("swap: 入口: ", "sourceIndex=" + sourceIndex, ";targetIndex=" + targetIndex, ";");
        //log("swap: 交换前: ", Arrays.toString(array));
        int tempValue = array[targetIndex];
        array[targetIndex] = array[sourceIndex];
        array[sourceIndex] = tempValue;
        //log("swap: 交换后: ", Arrays.toString(array));
    }

    // 测试: main方法
    public static void main(String[] args) {
        // 生成随机数组
        int[] array = randomArray(10);
        // 日志输出数组内容
        log("main: 排序前: ", Arrays.toString(array));
        // 排序
        sort(array);
        // 日志输出数组内容
        log("main: 排序后: ", Arrays.toString(array));
        // 检查是否符合升序
        // checkAsc(array);
    }

    // 是否开启日志;
    private static AtomicBoolean logOpenFlag = new AtomicBoolean(true);

    private static void log(String... str) {
        if (!logOpenFlag.get()) {
            return;
        }
        if (null == str) {
            return;
        }
        for (String s : str) {
            System.out.print(s);
        }
        System.out.println();
    }

    private static int[] randomArray(int length) {
        int[] array = new int[length];
        // 随机数; 此处没有指定种子
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            // 范围: [0, bound)
            array[i] = random.nextInt(length * 2);
        }

        return array;
    }

}
