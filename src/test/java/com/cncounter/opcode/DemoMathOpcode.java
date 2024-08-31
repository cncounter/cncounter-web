package com.cncounter.opcode;

/**
 * 演示数学运算相关的操作码; 这些方法纯粹是为了演示;
 */
public class DemoMathOpcode {

    public static int testIntOperate() {
        //  bipush        16
        //  istore_0
        int age = 16;
        //  iconst_2
        //  istore_1
        int year = 2;
        //  iload_0
        //  iload_1
        //  iadd
        //  istore_2
        int newAge = age + year;
        //  iload_2
        //  iload_0
        //  isub
        //  istore_3
        int ageChanged = newAge - age;
        //  sipush        365
        //  istore        4
        int daysPerYear = 365;
        //  iload_3
        //  iload         4
        //  imul
        //  istore        5
        int days = ageChanged * daysPerYear;
        //  iload         5
        //  iload         4
        //  idiv
        //  istore        6
        int yearChange = days / daysPerYear;
        //  iload         6
        //  bipush        100
        //  irem
        //  istore        7
        int intMod = yearChange % 100;
        //  iload         7
        //  ineg
        //  istore        8
        int negInt = -intMod;
        //  iload         8
        //  ireturn
        return negInt;
    }

    public static long testLongOperate() {
        //
        long startMillis = System.currentTimeMillis();
        //
        long spendMillis = 3000L;
        //
        long millisLater = startMillis + spendMillis;
        //
        long millisDiff = millisLater - startMillis;
        //
        long microseconds = millisDiff * 1000L;
        //
        long seconds = microseconds / 1000_000L;
        //
        long modLong = seconds % 60L;
        //
        long negLong = -modLong;
        //
        return negLong;
    }

    public static void testFloatAddSub() {

        float pi = 3.14f;
        float twoPi = pi + pi;
        float piDiff = twoPi - pi;
    }

    public static void testDoubleAddSub() {

        double radius = 2.0d;
        double diameter = radius + radius;
        double radiusDiff = diameter - radius;
    }

    public static void testMultiplyDiv() {
        int side = 126;
        int length = side * 4;
        int side2 = length / 2;

        long minuteMillis = 60L * 1000L;
        long secondMillis = minuteMillis / 60L;

        float pi = 3.14f;
        float diameter = 2.0f;
        float perimeter = pi * diameter;
        float halfPeri = perimeter / 2;

        double area = Math.PI * 2 * 2;
        double halfArea = area / 2;
    }

    public static void testRemNeg() {
        int x = 34;
        int modInt = x % 32;
        int negInt = -modInt;

        long value = 126L;
        long modLong = value % 60;
        long negLong = -modLong;

        float angle = 380.0f;
        float real = angle % 360f;
        float negFloat = -real;

        double pi = Math.PI;
        double decimal = pi % 3;
        double negPi = -pi;
    }

    public static void testShift() {
        int point = 100;
        int score = point << 1;
        int quarter = score >> 2;
        int unsignedInt = quarter >>> 30;

        long salary = 1024L;
        long bonus = salary << 2;
        long tax = bonus >> 3;
        long unsignedLong = tax >>> 60;
    }

    public static void testAndOr() {
        int bits = 1688;
        int mask = 0xFF;
        int andResult = bits & mask;
        int orResult = bits | mask;
        int xorResult = bits ^ mask;


    }

    public static void testInc() {
        int i = 2024;
        i++;
        int next2 = ++i;
        next2 += 6;
    }


    public static void main(String[] args) {
    }
}