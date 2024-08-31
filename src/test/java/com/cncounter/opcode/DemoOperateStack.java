package com.cncounter.opcode;

/**
 * 演示操作数栈相关的操作码; 这些方法纯粹是为了演示;
 */
public class DemoOperateStack {

    public static void testPop() {
        //  iconst_0, iconst_1
        //  invokestatic  #2
        //  pop
        Math.max(0, 1);
        //  lconst_1, lconst_0
        //  invokestatic  #3
        //  pop2
        Math.max(1L, 0L);
        //  ldc2_w        #4
        //  ldc2_w        #6
        //  invokestatic  #8
        //  pop2
        Math.max(3.14D, 1.71D);
        //  invokestatic  #9
        //  pop2
        System.currentTimeMillis();
        //  new           #10
        //  dup
        //  invokespecial #1
        //  pop
        new Object();
    }

    public static int testDup() {
        int num0, num1;
        //  sipush        666
        //  dup
        //  istore_0
        //  dup
        //  istore_1
        //  dup
        //  istore_1
        //  istore_0
        num0 = num1 = num1 = num0 = 666;
        long max1, max2, temp;
        temp = Math.max(0L, 2L);
        //  lload         6
        //  dup2
        //  lstore        4
        //  dup2
        //  lstore        4
        //  lstore_2
        max1 = max2 = max2 = temp;
        //  iload_0
        //  ireturn
        return num0;
    }

    public static void main(String[] args) {
    }
}