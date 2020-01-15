package jvm.chapter03;

import java.util.concurrent.TimeUnit;

public class DeadLockSample {
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    private static class ThreadTask1 implements Runnable {
        public void run() {
            synchronized (lock1) {
                System.out.println("lock1 by thread:" + Thread.currentThread().getId());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("lock2 by thread:" + Thread.currentThread().getId());
                }
            }
        }
    }

    private static class ThreadTask2 implements Runnable {
        public void run() {
            synchronized (lock2) {
                System.out.println("lock2 by thread:" + Thread.currentThread().getId());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("lock1 by thread:" + Thread.currentThread().getId());
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadTask1 task1 = new ThreadTask1();
        ThreadTask2 task2 = new ThreadTask2();
        //
        new Thread(task1).start();
        new Thread(task2).start();
    }
}