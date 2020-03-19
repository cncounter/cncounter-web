package demo.jvm0205;

import java.util.Random;
import java.util.concurrent.TimeUnit;

// 模拟重型任务
public class DemoHeavyTask implements Runnable {
    // 线程的名称前缀
    private int taskId;

    public DemoHeavyTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        // 执行一些业务逻辑
        try {
            int mod = taskId % 50;
            if (0 == mod) {
                // 模拟死等;
                synchronized (this) {
                    this.wait();
                }
            }
            // 模拟耗时任务
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(400) + 50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String threadName = Thread.currentThread().getName();
        System.out.println("JVM核心技术:" + taskId + "; by:" + threadName);
    }
}