package demo.jvm0205;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池示例;
 */
public class GitChatThreadDemo {

    public static void main(String[] args) throws Exception {
        // 1. 线程工厂
        DemoThreadFactory threadFactory
                = new DemoThreadFactory("JVM.GitChat");
        // 2. 创建Cached线程池; FIXME: 其实这里有坑...
        ExecutorService executorService =
                Executors.newCachedThreadPool(threadFactory);
        // 3. 提交任务;
        int taskSum = 1000;
        for (int i = 0; i < taskSum; i++) {
            // 执行任务
            executorService.execute(new DemoHeavyTask(i + 1));
            // 提交任务的间隔时间
            TimeUnit.MILLISECONDS.sleep(5);
        }
        // 4. 关闭线程池
        executorService.shutdown();
    }

}
