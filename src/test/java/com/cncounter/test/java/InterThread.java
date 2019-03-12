package com.cncounter.test.java;

import java.util.concurrent.TimeUnit;

/**
 * 演示线程间通信的示例代码
 */
public class InterThread {

    public static void main(String[] args) {
//        demo1();
//        demo2();
//        demo3();
        demo4();

    }

    private static void printNumber(String threadName) {
        int j = 0;
        while (j++ < 3) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + " print: " + j);
        }
    }

    /**
     * AB启动的顺序是随机的, 可多次执行来验证
     */
    private static void demo1() {
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                printNumber("A");
            }
        });

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                printNumber("B");
            }
        });

        A.start();
        B.start();
    }

    /**
     * 打印顺序: A 1, A 2, A 3, B 1, B 2, B 3
     */
    private static void demo2() {
        final Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                printNumber("A");
            }
        });

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("B线程需要等待A线程执行完成");
                try {
                    A.join();// 等待线程A执行完成之后与当前线程“汇合”
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                printNumber("B");
            }
        });

        A.start();
        B.start();
    }

    /**
     * 打印顺序: A 1, B 1, B 2, B 3, A 2, A 3
     */
    private static void demo3() {
        final Object lock = new Object();
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("A 1");
                    try {
                        System.out.println("A waiting…");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("A 2");
                    System.out.println("A 3");
                }
            }
        });
        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("B 1");
                    System.out.println("B 2");
                    System.out.println("B 3");
                    lock.notify();
                }
            }
        });
        A.start();
        //
        try {
            TimeUnit.MILLISECONDS.sleep(50L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        B.start();
    }
    /**
     * demo3的基础上-加日志
     * 打印顺序: A 1, B 1, B 2, B 3, A 2, A 3
     */
    private static void demo4() {
        final Object lock = new Object();

        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("====提示: A 等待锁...");
                synchronized (lock) {
                    System.out.println("====提示: A 得到了锁 lock");
                    System.out.println("A 1");
                    try {
                        System.out.println("====提示: A 调用lock.wait()放弃锁的控制权,并等待...");
                        lock.wait();
                        System.out.println("====提示: A在lock.wait()之后,再次获得锁的控制权,HAHAHA");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("====提示: A线程被唤醒, A 重新获得锁 lock");
                    System.out.println("A 2");
                    System.out.println("A 3");
                }

            }
        });

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("====提示: B 等待锁...");
                synchronized (lock) {
                    System.out.println("====提示: B 得到了锁 lock");
                    System.out.println("B 1");
                    System.out.println("B 2");
                    System.out.println("B 3");

                    System.out.println("====提示: B 打印完毕，调用 lock.notify() 方法");
                    lock.notify();
                    // 看看A能不能获得锁
                    try {
                        System.out.println("====提示: B 调用 lock.notify()完成,睡1秒看看...");
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("====提示: B 调用 lock.notify()完成,退出synchronized块");
                }
            }
        });

        A.start();
        //
        try {
            TimeUnit.MILLISECONDS.sleep(50L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //
        B.start();
    }

}