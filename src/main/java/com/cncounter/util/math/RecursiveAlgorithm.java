package com.cncounter.util.math;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 递归算法
public class RecursiveAlgorithm {
    // 斐波纳契结果缓存
    private static Map<Integer, Integer> fibonacciCache
            = new ConcurrentHashMap<Integer, Integer>();

    // 斐波纳契
    public static int fibonacci(int n) {
        if (n < 0) {
            throw new IllegalStateException("must be positive: n=" + n);
        }
        if (n <= 1) {
            return n;
        }
        Integer result = fibonacciCache.get(n);
        if (null != result) {
            return result;
        }
        result = fibonacci(n - 2) + fibonacci(n - 1);
        fibonacciCache.put(n, result);
        return result;
        // 后续优化: 动态规划
    }

    // 回文
    public static boolean palindrome(String text) {
        int size = text.length();
        if (size <= 1) {
            return true;
        }
        String first = text.substring(0, 1);
        String end = text.substring(size - 1, size);
        if (!first.equalsIgnoreCase(end)) {
            return false;
        } else {
            String subText = text.substring(1, size - 1);
            return palindrome(subText);
        }
    }

    // 阶乘
    public static int factorial(int n) {
        if (n < 0) {
            throw new IllegalStateException("must be positive: n=" + n);
        }
        if (0 == n) {
            return 1;
        } else {
            // a smaller sub-problem
            return n * factorial(n - 1);
        }
    }

    public static void main(String[] args) {
        //factorialTest();
        //palindromeTest();
        fibonacciTest();
    }

    private static void factorialTest() {
        int max = 20;
        for (int n = 0; n < max; n++) {
            int result = factorial(n);
            if (n >= 14) {
                System.err.println(n + " = " + n + "; Integer overflow!");
                continue;
            }
            System.out.println("" + n + "! = " + result);
        }
    }

    private static void palindromeTest() {
        Map<String, Boolean> textCheckMap = new HashMap<String, Boolean>();
        textCheckMap.put("163@361", Boolean.TRUE);
        textCheckMap.put("361ko@ok163", Boolean.TRUE);
        textCheckMap.put("361ko@@ok163", Boolean.TRUE);
        textCheckMap.put("361ko+=ok163", Boolean.FALSE);
        //
        for (String text : textCheckMap.keySet()) {
            boolean result = palindrome(text);
            if (result != textCheckMap.get(text)) {
                throw new IllegalStateException("result check fail: text=" + text);
            }
            System.out.println("text=" + text + "; result= " + result);
        }
    }

    private static void fibonacciTest() {
        int max = 46;
        for (int n = 0; n <= max; n++) {
            int result = fibonacci(n);
            System.out.println("fibonacci(" + n + ") = " + result);
        }
    }
}
