package jvm.chapter03;

public class JMMSample1 {
    // 请分析极端情况下, 是否会输出 "r1=2并且r2=2"的情形?
    private static int A, B = 0;

    private static class ThreadTask1 implements Runnable {
        public void run() {
            int r1 = A;
            B = 2;
            System.out.println("r1=" + r1);
        }
    }

    private static class ThreadTask2 implements Runnable {
        public void run() {
            int r2 = B;
            A = 2;
            System.out.println("r2=" + r2);
        }
    }

}
