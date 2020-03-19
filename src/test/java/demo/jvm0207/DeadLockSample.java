package demo.jvm0207;

import java.util.concurrent.TimeUnit;

public class DeadLockSample {
    private static Object lockA = new Object();
    private static Object lockB = new Object();

    public static void main(String[] args) {
        ThreadTask1 task1 = new ThreadTask1();
        ThreadTask2 task2 = new ThreadTask2();
        //
        new Thread(task1).start();
        new Thread(task2).start();
    }

    private static class ThreadTask1 implements Runnable {
        public void run() {
            synchronized (lockA) {
                System.out.println("lockA by thread:"
                        + Thread.currentThread().getId());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockB) {
                    System.out.println("lockB by thread:"
                            + Thread.currentThread().getId());
                }
            }
        }
    }

    private static class ThreadTask2 implements Runnable {
        public void run() {
            synchronized (lockB) {
                System.out.println("lockB by thread:"
                        + Thread.currentThread().getId());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockA) {
                    System.out.println("lockA by thread:"
                            + Thread.currentThread().getId());
                }
            }
        }
    }
}