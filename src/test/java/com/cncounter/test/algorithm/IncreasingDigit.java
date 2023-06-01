package com.cncounter.test.algorithm;

/*
738. 单调递增的数字

参考:

https://leetcode.cn/classic/problems/monotone-increasing-digits/description/

当且仅当每个相邻位数上的数字 x 和 y 满足 x <= y 时，我们称这个整数是单调递增的。

给定一个整数 n ，返回 小于或等于 n 的最大数字，且数字呈 单调递增 。


示例 1:

输入: n = 10
输出: 9

示例 2:

输入: n = 1234
输出: 1234

示例 3:

输入: n = 332
输出: 299


提示:

0 <= n <= 10^9

 */
public class IncreasingDigit {
    public int monotoneIncreasingDigits(int n) {
        int result = n;
        // 算法:
        // 1. 判断本身
        // 2. 依次除以 modNum 再乘以modNum; 10, 100, 1000, 10000  并减1, 判断
        int modNum = 1;
        for (int i = 0; i < 9; i++) {
            // check
            boolean checkPass = true;
            String str = Integer.toString(result);
            char prev = str.charAt(0);
            for (int index = 1; index < str.length(); index++) {
                if (prev > str.charAt(index)) {
                    checkPass = false;
                    break;
                } else {
                    prev = str.charAt(index);
                }
            }
            if (checkPass) {
                return result;
            }
            //
            result = (n / modNum * modNum) - 1;
            modNum *= 10;
        }

        return result;
    }

    public static void main(String[] args) {
        IncreasingDigit solution = new IncreasingDigit();
        //
        {
            int n = 10;
            int result = solution.monotoneIncreasingDigits(n);
            System.out.println("输入: n = " + n);
            System.out.println("输出: " + result);
            System.out.println("==========");
        }
        {
            int n = 1234;
            int result = solution.monotoneIncreasingDigits(n);
            System.out.println("输入: n = " + n);
            System.out.println("输出: " + result);
            System.out.println("==========");
        }
        {
            int n = 332;
            int result = solution.monotoneIncreasingDigits(n);
            System.out.println("输入: n = " + n);
            System.out.println("输出: " + result);
            System.out.println("==========");
        }
    }
}
