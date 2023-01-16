package com.cncounter.test.algorithm;

import java.util.Arrays;
import java.util.Random;

/*
43. 字符串相乘:
给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。

注意：不能使用任何内置的 BigInteger 库或直接将输入转换为整数。

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/multiply-strings
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
*/
public class MultiplyStrings {
    public static void main(String[] args) {
        int loop = 100;
        for (int i = 0; i < loop; i++) {
            String num1 = randomNum(20);
            String num2 = randomNum(20);
            String result = new MultiplyStrings().multiply(num1, num2);
            System.out.println(num1 + " * " + num2 + " == " + result);
        }

    }

    private static String randomNum(int placeNum) {
        StringBuilder builder = new StringBuilder();
        Random r = new Random();
        int len = r.nextInt(placeNum) + 1;
        for (int i = 0; i < len; i++) {
            int n = r.nextInt(10);
            if (0 == n && 0 == builder.length()) {
                continue;
            }
            builder.append(n);
        }
        return builder.toString();
    }

    public String multiply(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        // 转换为int数组
        int[] array1 = new int[len1];
        int[] array2 = new int[len2];
        for (int i = 0; i < len1; i++) {
            array1[i] = num1.charAt(i) - '0';
        }
        for (int i = 0; i < len2; i++) {
            array2[i] = num2.charAt(i) - '0';
        }
        // 临时输出查看转换结果
        // System.out.println("array1=" + Arrays.toString(array1));
        // System.out.println("array2=" + Arrays.toString(array2));
        // 比 array2 多1位
        int midArrayLen = len2 + 1;
        // 使用二维数组存放中间结果
        int[][] middleResult = new int[len1][midArrayLen];
        // 双层遍历相乘
        for (int p1 = len1 - 1; p1 >= 0; p1--) {
            int curNum1 = array1[p1];
            int[] curMidResult = new int[midArrayLen];
            for (int p2 = len2 - 1; p2 >= 0; p2--) {
                int curNum2 = array2[p2];
                // 按位相乘
                int temp = curNum1 * curNum2;
                // 先直接存放临时结果
                curMidResult[p2 + 1] = temp;
            }
            // 修正临时结果
            for (int p = midArrayLen - 1; p >= 0; p--) {
                int num = curMidResult[p]; // 两个1位数字相乘 : 只会是1-2位数;
                if (num < 10) {
                    continue;
                }
                // 大于9的数字: 进位
                curMidResult[p] = num % 10;
                curMidResult[p - 1] += num / 10;
            }
            //
            middleResult[p1] = curMidResult;
            // System.out.println("p1=" + p1 + "; curNum1=" + curNum1 + "; curMidResult=" + Arrays.toString(curMidResult));
        }

        // 最终结果的最大位数
        int finalMaxLen = len1 + len2;
        int[] finalResult = new int[finalMaxLen];
        // 把结果加到最终结果之中;
        // 拆开计算
        for (int p1 = len1 - 1; p1 >= 0; p1--) {
            int[] curMidResult = middleResult[p1];
            // 每个数字额外附带的权重:
            int w1 = len1 - p1 - 1;
            for (int i = curMidResult.length - 1; i >= 0; i--) {
                // 值
                int num = curMidResult[i];
                int wm = curMidResult.length - i - 1;
                int fi = finalMaxLen - 1 - w1 - wm;
                finalResult[fi] += num;
            }
            // 修正 finalResult 的进位
            for (int i = finalMaxLen - 1; i >= 0; i--) {
                int num = finalResult[i]; // 两个1位数字相加 : 只会是1-2位数;
                if (num < 10) {
                    continue;
                }
                // 大于9的数字: 进位
                finalResult[i] = num % 10;
                finalResult[i - 1] += num / 10;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < finalMaxLen; i++) {
            int n = finalResult[i];
            if (0 == n && 0 == builder.length()) {
                continue;
            }
            if (n >= 10) {
                throw new RuntimeException("计算错误: finalMaxLen: i=" + i + "; n = " + n);
            }
            builder.append(n);
        }
        String result =  builder.toString();
        if(result.isEmpty()){
            return "0";
        }
        return result;
    }
}
