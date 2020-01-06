package jvm.chapter03;

import java.util.concurrent.CountDownLatch;

public class ThreadUnSafeTask implements Runnable {
    public static final int loopTimes = 10000000;
    private int sum = 0;
    private CountDownLatch latch;

    @Override
    public void run() {
        for (int i = 0; i < loopTimes; i++) {
            this.sum++;
        }
        latch.countDown();
    }

    public static void main(String[] args) {
        int threadNum = 10;
        ThreadUnSafeTask task = new ThreadUnSafeTask();
        task.latch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            new Thread(task).start();
        }
        try {
            task.latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("expected: " + threadNum * loopTimes + "; task.sum:" + task.sum);
    }
}
