package com.cncounter.opcode;

/**
 * 演示常量相关的操作码
 */
public class DemoConstantsOpcode {

    public static void testConstOpcode() {
        int m1 = -1; // iconst_m1; istore_0;
        int i0 = 0;  // iconst_0; istore_1;
        int i1 = 1;  // iconst_1; istore_2;
        int i2 = 2;  // iconst_2; istore_3;
        int i3 = 3;  // iconst_3; istore 4;
        int i4 = 4;  // iconst_4; istore 5;
        int i5 = 5;  // iconst_5; istore 6;

        long l0 = 0L;     // lconst_0; lstore 7;
        long l1 = 1L;     // lconst_1; lstore 9;
        float f0 = 0F;    // fconst_0; fstore 11;
        float f1 = 1F;    // fconst_1; fstore 12;
        float f2 = 2F;    // fconst_2; fstore 13;
        double d0 = 0D;   // dconst_0; dstore 14;
        double d1 = 1D;   // dconst_1; dstore 16;

        int i127 = 127;  // bipush 127; istore 18;
        int i128 = 128;  // sipush 128; istore 19;

        Object obj = null;  // aconst_null; astore 20;
        float f520 = 5.20f; // ldc #2 <5.2>; fstore 21;
        String name = "tiemao"; // ldc #3 <tiemao>; astore 22;
        long l65536 = 65536L;   // ldc2_w #4 <65536>; lstore 23;
        double d86400 = 86400.0D; // ldc2_w #6 <86400.0>; dstore 25;
        double d00 = 0.0D;        // dconst_0; dstore 27;
    }

    public static void main(String[] args) {
        testConstOpcode();
    }
}