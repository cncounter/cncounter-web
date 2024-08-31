package com.cncounter.opcode;

/**
 * 演示操作数栈相关的操作码; 这些方法纯粹是为了演示;
 */
public class DemoControlOpcode {

    public static int testInc() {
        int num = 1;
        num++;
        ++num;
        return num++;
    }

    public static int max(int num1, int num2) {
        return num1 > num2 ? num1 : num2;
    }

    public static long max(long num1, long num2) {
        return num1 > num2 ? num1 : num2;
    }


    public static void main(String[] args) {
    }
}