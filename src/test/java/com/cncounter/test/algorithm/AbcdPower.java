package com.cncounter.test.algorithm;

/*
穷举证明: A^b * C^d = AbCd
结果集: 2^5 + 9^2 = 2592
 */
public class AbcdPower {
    public static void main(String[] args) {
        // 依次遍历迭代
        for (int a = 1; a <= 9; a++) {
            for (int b = 1; b <= 9; b++) {
                for (int c = 1; c <= 9; c++) {
                    for (int d = 1; d <= 9; d++) {
                        testPower(a, b, c, d);
                    }
                }
            }
        }
    }

    private static void testPower(int a, int b, int c, int d) {
        Double p = Math.pow(a, b) * Math.pow(c, d);
        Integer result = p.intValue();
        // 排除不是4位数的值
        if (result > 9999 || result < 1000) {
            return;
        }
        if (result.toString().equals("" + a + b + c + d)) {
            // 2^5 + 9^2 = 2592
            System.out.println("" + a + "^" + b + " * " + c + "^" + d + " = " + result);
        }
        // 也可以按位比较
        // 千位: t=thousand;
        int t = result / 1000;
        // 百位: h=hundred
        int h = (result % 1000) / 100;
        // 十位: s=tens
        int s = (result % 100) / 10;
        // 个位: o=one
        int o = (result % 10);
        if (a == t && b == h && c == s && d == o) {
            // 2^5 + 9^2 = 2592
            System.out.println("" + a + "^" + b + " * " + c + "^" + d + " = " + result);
        }

    }
}
