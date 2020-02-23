package demo.jvm0205;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试线程。。。
 * -Xss1m
 */
public class TestThreadMore implements Runnable {
    // 已经开始执行的线程数量
    private static AtomicInteger enteredNum;
    // 完成的栏杆
    private static CountDownLatch finishLatch;
    private static CountDownLatch startLatch;
    // 线程睡眠的秒数
    private static long sleepSeconds = 10;

    //
    public static void main(String[] args) {
        // 1. 创建线程池
        ExecutorService executorService =
                Executors.newCachedThreadPool(threadFactory);
        // 2. 提交任务;
        testThreadCount(executorService, 1000);
        testThreadCount(executorService, 4040);
        testThreadCount(executorService, 4140);
        // 3. 关闭线程池
        executorService.shutdownNow();
    }

    private static void testThreadCount(
            ExecutorService executorService, int threadSum) {
        enteredNum = new AtomicInteger(0);
        startLatch = new CountDownLatch(threadSum);
        finishLatch = new CountDownLatch(threadSum);
        //
        System.out.println("=====before.execute:" + threadSum);
        printMemory();
        //
        for (int i = 0; i < threadSum; i++) {
            executorService.execute(new TestThreadMore());
        }
        // 3.1 JVM内存信息
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        // memoryMXBean.
        // 等待
        try {
            startLatch.await();
        } catch (InterruptedException e) {
        }
        // 打印当前的内存状态
        System.out.println("=====All.Started:" + threadSum);
        printMemory();
        //
        try {
            finishLatch.await();
        } catch (InterruptedException e) {
        }
        // 打印当前的内存状态
        System.out.println("=====All.Finish:" + threadSum);
        printMemory();

    }

    //
    private static void printMemory() {
        // 当前分配的堆内存; -Xms
        long totalMemory = Runtime.getRuntime().totalMemory();
        // 堆内存最大值; -Xmx
        long maxMemory = Runtime.getRuntime().maxMemory();
        // 可用内存 =
        long freeMemory = Runtime.getRuntime().freeMemory();
        // 可用处理器数量
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        // 最大直接内存数量; -Xmx
        long maxDirectMemory = sun.misc.VM.maxDirectMemory();


        //
        System.out.println(""
                + "; availableProcessors=" + availableProcessors
                + "; totalMemory=" + mb(totalMemory)
                + "; maxMemory=" + mb(maxMemory)
                + "; freeMemory=" + mb(freeMemory)
                + "; maxDirectMemory=" + mb(maxDirectMemory)
        );
        System.out.println("usedMemory=" + mb(totalMemory - freeMemory));

    }

    private static String mb(long num) {
        return num / (1024 * 1024) + "MB";
    }


    @Override
    public void run() {
        // 打印日志
        int curNum = enteredNum.incrementAndGet();
        // 标记开始
        startLatch.countDown();
        // System.out.println("curNum=" + curNum + ";ThreadId=" + Thread.currentThread().getId());
        // 模拟耗时操作
        try {
            TimeUnit.SECONDS.sleep(sleepSeconds);
        } catch (InterruptedException e) {
            // 被吵醒了怎么处理? 主要是在循环或大型任务中, 决定是否继续执行或退出
        }
        // 标记完成
        finishLatch.countDown();
    }

    public static ThreadFactory threadFactory = new ThreadFactory() {
        // 自己设置线程ID计数器
        private AtomicInteger threadIdAdder = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            // 创建新线程
            Thread t = new Thread(r);
            // 设置一个有意义的名字
            t.setName("testManyThread-" + threadIdAdder.incrementAndGet());
            // 设置为守护线程
            t.setDaemon(Boolean.TRUE);
            // 设置优先级; 比如我们有多个线程池,分别处理普通任务和紧急任务。
            t.setPriority(Thread.MAX_PRIORITY);
            // 设置某个类的或者自定义的的类加载器
            // t.setContextClassLoader();
            // 设置此线程的最外层异常处理器
            // t.setUncaughtExceptionHandler();
            // 不启动; 直接返回;
            return t;
        }
    };
}
