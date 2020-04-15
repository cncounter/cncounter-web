package demo.jvm0207;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeadLockSample2 {

    public static void main(String[] args) throws Exception {
        DeadLockTask deadLockTask = new DeadLockTask();
        // 多线程模拟死锁
        new Thread(deadLockTask).start();
        new Thread(deadLockTask).start();
        // 等待状态
        Thread wt = new WaitedThread();
        wt.start();
        // 当前线程等待另一个线程来汇合
        wt.join();
    }

    private static class WaitedThread extends Thread {
        @Override
        public void run() {
            synchronized (DeadLockSample2.class) {
                try {
                    DeadLockSample2.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 简单的死锁; 分别锁2个对象
    private static class DeadLockTask implements Runnable {
        private Object lockA = new Object();
        private Object lockB = new Object();
        private AtomicBoolean flag = new AtomicBoolean(false);

        public void run() {
            try {
                if (flag.compareAndSet(false, true)) {
                    synchronized (lockA) {
                        TimeUnit.SECONDS.sleep(2);
                        synchronized (lockB) {
                            System.out.println("死锁内部代码");
                        }
                    }
                } else {
                    synchronized (lockB) {
                        TimeUnit.SECONDS.sleep(2);
                        synchronized (lockA) {
                            System.out.println("死锁内部代码");
                        }
                    }
                }

            } catch (Exception e) {
            }
        }
    }

}