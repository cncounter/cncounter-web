package com.cncounter.util.math;

import java.util.HashMap;
import java.util.Map;

// 递归算法
public class RecursiveAlgorithm {
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
        factorialTest();
        palindromeTest();
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
}
