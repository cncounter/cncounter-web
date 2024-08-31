package com.cncounter.opcode;

import java.util.Arrays;

/**
 * 演示常量相关的操作码; 这些方法纯粹是为了演示;
 */
public class DemoLoadOpcode {

    public static void testIntLoad(int num0, int num1, int num2,
                                   int num3, int num4) {
        // 每个int参数占一个局部变量表槽位
        // iload_0; iload_1; iadd; iload_2; iadd;
        // iload_3; iadd; iload 4; iadd; istore 5;
        int total = num0 + num1 + num2 + num3 + num4;
        // 所以 total 排到第5号槽位
        // iload 5; iload 5;
        Integer.valueOf(total);
    }

    public static void testLongLoad(long num0, long num1, long num2) {
        // 每个 long 型入参占局部变量表的2个槽位; 所以槽位是 0-2-4
        // lload_0; lload_2; ladd; lload 4; ladd;
        Long.valueOf(num0 + num1 + num2);
    }

    public void testInstanceLongLoad(long num1, long num2) {
        // 实例方法中, 局部变量表的0号槽位被 this 占了
        // 然后是方法入参, 每个long占2个槽位
        // aload_0; lload_1; l2d; lload_3; l2d;
        this.testInstanceDoubleLoad(num1, num2);
    }

    public static void testFloatLoad(float num0, float num1,
                                     float num2, float num3, float num4) {
        // fload_0; fload_1; fadd; fload_2; fadd;
        // fload_3; fadd; fload 4; fadd;
        Float.valueOf(num0 + num1 + num2 + num3 + num4);
    }

    public static void testDoubleLoad(double num0,
                                      double num1, double num2) {
        // 每个 double 型入参占2个槽位
        // dload_0; dload_2; dadd; dload 4; dadd;
        Double.valueOf(num0 + num1 + num2);
    }

    // FIXME: 这是一个死循环递归方法, 此处仅用于演示
    public void testInstanceDoubleLoad(double num1, double num2) {
        // 实例方法, 局部变量表的0号槽位同来存放 this
        // aload_0; dload_1; dload_3;
        testInstanceDoubleLoad(num1, num2);
    }

    public static void testReferenceAddrLoad(String str0, Object obj1,
                                             Integer num2, Long num3, Float num4, Double num5) {
        // 方法每个 obj 参数占一个槽位; 部分字节码:
        // aload_0; aload_1; aload_2; aload_3; aload 4; aload 5
        Arrays.asList(str0, obj1, num2, num3, num4, num5);
    }

    public static void testArrayLoad(
            int[] array0, long[] array1, float[] array2,
            double[] array3, String[] array4, boolean[] array5,
            byte[] array6, char[] array7, short[] array8) {
        // 这几个操作的字节码套路都是一样的:
        // 数组引用; 下标;     数组取值; 赋值给局部变量;
        // aload_0; iconst_0; iaload; istore 9;
        int num0 = array0[0];
        // aload_1; iconst_1; laload; lstore 10;
        long num1 = array1[1];
        // aload_2; iconst_2; faload; fstore 12;
        float num2 = array2[2];
        // aload_3; iconst_3; daload; dstore 13;
        double num3 = array3[3];
        // aload 4; iconst_4; aaload; astore 15;
        Object obj4 = array4[4];
        // aload 5; iconst_5; baload; istore 16;
        boolean bool5 = array5[5];
        // aload 6; bipush 6; baload; istore 17;
        byte byte6 = array6[6];
        // aload 7; bipush 7; caload; istore 18;
        char char7 = array7[7];
        // aload 8; bipush 8; saload; istore 19;
        short num8 = array8[8];
    }

    public static void main(String[] args) {
    }
}