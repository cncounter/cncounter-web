package com.cncounter.test.java;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示 volatile 关键字与 Atomic类的使用

 volatile要求程序对变量的每次修改，都写回主内存，这样便对其它线程课件，解决了可见性的问题，但是不能保证数据的一致性

 */
public class TestVolatileAtomic {
    //
    // 注意不要超出int的取值范围+-22亿;
    public static int counter1 = 0;
    // volatile
    public static volatile int counter2 = 0;
    // JDK内置的原子递增类
    public static AtomicInteger counter3 = new AtomicInteger(0);

    //
    public static void main(String[] args) {
        //
//        demo1();
//        demo2();
        demo3();
    }

    /*
demo1结果说明:

某一次的执行结果如下:
===================================::::::demo1
====loopCount:100000000; ====threadNum:6; costMillis=26
====expect   :600000000; ====counter1:102689274

8核CPU的机器上执行, 线程数量: 6,每个线程执行1亿次递增操作;消耗时间 26ms；
期待结果为 6亿, 实际取得 counter1 的值为1亿多一点。
我们知道了操作属于非线程安全, 可以推断出: counter1 的取值范围为: 1亿~6亿;

*/
    // 演示多线程执行 int 递增操作
    private static void demo1() {
        System.out.println("===================================::::::demo1");
        //
        long startMillis = System.currentTimeMillis();
        // 每个线程的循环次数
        final int loopCount = 10000 * 10000;
        // 执行线程数
        final int threadNum = 6;
        // 递减等待栅栏
        final CountDownLatch latch1 = new CountDownLatch(threadNum);
        // 任务逻辑代码
        Runnable addTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < loopCount; i++) {
                    counter1++;
                }
                // 栅栏减一;表示操作完成
                latch1.countDown();
            }
        };
        // 启动线程
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(addTask);
            thread.start();
        }
        // 等待所有线程执行完毕
        try {
            latch1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long costMillis = System.currentTimeMillis() - startMillis;
        // 输出执行结果
        System.out.println("====loopCount:" + loopCount + "; ====threadNum:" + threadNum + "; costMillis=" + costMillis);
        System.out.println("====expect   :" + (threadNum * loopCount) + "; ====counter1:" + counter1);

    }

    /*
demo2结果说明:

某一次的执行结果如下:
===================================::::::demo2
====loopCount:100000000; ====threadNum:6; costMillis=11844
====expect   :600000000; ====counter2:138308664

8核CPU的机器上执行, 线程数量: 6,每个线程执行1亿次递增操作;消耗时间 11844 ms,约12秒；
期待结果为 6亿, 实际取得 counter2 的值也只是1亿多一点。
我们知道了操作属于非线程安全, 可以推断出: counter2 的取值范围为: 1亿~6亿;

*/
    // 演示多线程执行 volatile int 递增操作; volatile也不能保证线程安全；
    // 只适合偶尔的单个线程写入, 之后其他线程进行读取时不会读取到历史值。
    private static void demo2() {
        System.out.println("===================================::::::demo2");
        //
        long startMillis = System.currentTimeMillis();
        // 每个线程的循环次数
        final int loopCount = 10000 * 10000;
        // 执行线程数
        final int threadNum = 6;
        // 递减等待栅栏
        final CountDownLatch latch2 = new CountDownLatch(threadNum);
        // 任务逻辑代码
        Runnable addTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < loopCount; i++) {
                    counter2++;
                }
                // 栅栏减一;表示操作完成
                latch2.countDown();
            }
        };
        // 启动线程
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(addTask);
            thread.start();
        }
        // 等待所有线程执行完毕
        try {
            latch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long costMillis = System.currentTimeMillis() - startMillis;
        // 输出执行结果
        System.out.println("====loopCount:" + loopCount + "; ====threadNum:" + threadNum + "; costMillis=" + costMillis);
        System.out.println("====expect   :" + (threadNum * loopCount) + "; ====counter2:" + counter2);

    }
/*

demo3结果说明:

某一次的执行结果如下:
===================================::::::demo3
====loopCount:100000000; ====threadNum:6; costMillis=13737
====expect   :600000000; ====counter3:600000000

8核CPU的机器上执行, 线程数量: 6,每个线程执行1亿次递增操作;消耗时间 13737 ms,约13秒, 和volatile的12秒差不多；
期待结果为 6亿, 实际取得 counter3 的值也是6亿。
我们知道了AtomicInteger的递增操作属于线程安全, 可以推断出: counter3 的值一定是6亿;


 */
    //
    private static void demo3() {
        System.out.println("===================================::::::demo3");
        //
        long startMillis = System.currentTimeMillis();
        // 每个线程的循环次数
        final int loopCount = 10000 * 10000;
        // 执行线程数
        final int threadNum = 6;
        // 递减等待栅栏
        final CountDownLatch latch3 = new CountDownLatch(threadNum);
        // 任务逻辑代码
        Runnable addTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < loopCount; i++) {
                    counter3.incrementAndGet();
                }
                // 栅栏减一;表示操作完成
                latch3.countDown();
            }
        };
        // 启动线程
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(addTask);
            thread.start();
        }
        // 等待所有线程执行完毕
        try {
            latch3.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long costMillis = System.currentTimeMillis() - startMillis;
        // 输出执行结果
        System.out.println("====loopCount:" + loopCount + "; ====threadNum:" + threadNum + "; costMillis=" + costMillis);
        System.out.println("====expect   :" + (threadNum * loopCount) + "; ====counter3:" + counter3);

    }
}
