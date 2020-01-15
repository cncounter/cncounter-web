package demo.jvm0207;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.LongAdder;

/**
 * 线程上下文切换的消耗
 */
public class ContextSwitchTest {
    private static Object lock = new Object();
    private static LongAdder counter = new LongAdder();
    private static AtomicBoolean flag = new AtomicBoolean(false);

    private static class AdderRunner implements Runnable {
        private final int target;
        private final boolean runFlag;

        public AdderRunner(int target, boolean runFlag) {
            this.target = target;
            this.runFlag = runFlag;
        }

        @Override
        public void run() {
            long startMillis = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " 启动时间:" + startMillis);
            while (counter.intValue() < target) {
                toAddAndWait();
            }
            long endMillis = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " 执行耗时:" + (endMillis - startMillis));
        }

        private void toAddAndWait() {
            synchronized (lock) {
                //
                if (flag.compareAndSet(runFlag, !runFlag)) {
                    counter.increment();
                }
                try {
                    lock.notify();
                    if (counter.intValue() < target) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        //
        int times = 100 * 10000;
        //
        AdderRunner r1 = new AdderRunner(times, Boolean.TRUE);
        AdderRunner r2 = new AdderRunner(times, Boolean.FALSE);
        //
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.setName("t1");
        t2.setName("t2");
        //
        t1.start();
        t2.start();
        // 考虑 synchronized 实现时使用了部分自旋操作。
/*
t1 执行耗时:5459
t2 执行耗时:5459
 */
    }
}
