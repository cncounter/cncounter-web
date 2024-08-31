package com.cncounter.opcode;

/**
 * 演示Store相关的操作码; 这些方法纯粹是为了演示;
 */
public class DemoStoreOpcode {

    public static void testIntStore() {
        int num0, num1, num2, num3, num4;
        //  bipush             -2
        //  dup,  istore        4
        //  dup,  istore_3
        //  dup,  istore_2
        //  dup,  istore_1
        //  istore_0
        num0 = num1 = num2 = num3 = num4 = -2;
    }

    public static void testLongStore() {
        long num0, num1, num2, num3, num4;
        //  ldc2_w              #2
        //  dup2,  lstore        8
        //  dup2,  lstore        6
        //  dup2,  lstore        4
        //  dup2,  lstore_2
        //  lstore_0
        num0 = num1 = num2 = num3 = num4 = 2024L;
    }

    public static void testFloatStore() {
        float num0, num1, num2, num3, num4;
        //  ldc                #4
        //  dup,  fstore        4
        //  dup,  fstore_3
        //  dup,  fstore_2
        //  dup,  fstore_1
        //  fstore_0
        num0 = num1 = num2 = num3 = num4 = 10.24f;
    }

    public static void testDoubleStore() {
        double num0, num1, num2, num3, num4;
        //    ldc2_w             #5
        //    dup2, dstore        8
        //    dup2, dstore        6
        //    dup2, dstore        4
        //    dup2, dstore_2
        //    dstore_0
        num0 = num1 = num2 = num3 = num4 = 24.0D;
    }

    public static void testAddressStore() {
        Integer num0, num1, num2, num3, num4;
        //  invokestatic       #7
        //  dup,  astore        4
        //  dup,  astore_3
        //  dup,  astore_2
        //  dup,  astore_1
        //  astore_0
        num0 = num1 = num2 = num3 = num4 = -3;
    }

    public static void testIntArrayStore() {
        //	iconst_4
        //	newarray       int
        //	astore_0
        int[] intArray = new int[4];
        //	aload_0,  iconst_0,  iconst_1
        //	iastore
        intArray[0] = 1;
        //	aload_0,  iconst_1,  iconst_2
        //	iastore
        intArray[1] = 2;
        //	aload_0,  iconst_2,  iconst_3
        //	iastore
        intArray[2] = 3;
        //	aload_0,  iconst_3,  iconst_4
        //	iastore
        intArray[3] = 4;
    }

    public static void main(String[] args) {
    }
}