package com.cncounter.util.math;

// 递归算法
public class RecursiveAlgorithm {
    // 阶乘
    public static int factorial(int n) {
        if (0 == n) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public static void main(String[] args) {
        int n = 10;
        int result = factorial(n);
        System.out.println("factorial(" + n + ") = " + result);
    }
}
