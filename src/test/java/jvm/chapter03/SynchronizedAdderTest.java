package jvm.chapter03;

import java.util.concurrent.TimeUnit;

public class SynchronizedAdderTest {

    public static void main(String[] args) {

        new Thread(){
            @Override
            public void run() {
                test1();
            }
        }.start();
        sleep10();
        new Thread(){
            @Override
            public void run() {
                test2();
            }
        }.start();
        sleep10();
        new Thread(){
            @Override
            public void run() {
                test2();
            }
        }.start();
        sleep10();
        new Thread(){
            @Override
            public void run() {
                test1();
            }
        }.start();
    }
    public static void sleep10() {
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test1() {
        SynchronizedAdder adder = new SynchronizedAdder();
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < 10000 * 10000; i++) {
            adder.incrementAndGet();
        }
        long endMillis = System.currentTimeMillis();
        System.out.println("test1.ms:" + (endMillis - startMillis));
    }

    public static void test2() {
        SynchronizedAdder adder = new SynchronizedAdder();
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < 10000 * 10000; i++) {
            adder.addAndGet();
        }
        long endMillis = System.currentTimeMillis();
        System.out.println("test2.ms:" + (endMillis - startMillis));
    }

}
