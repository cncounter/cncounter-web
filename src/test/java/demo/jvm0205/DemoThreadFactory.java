package demo.jvm0205;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

// Demo线程工厂
public class DemoThreadFactory implements ThreadFactory {
    // 线程的名称前缀
    private String threadNamePrefix;
    // 线程ID计数器
    private AtomicInteger counter = new AtomicInteger();

    public DemoThreadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        // 创建新线程
        Thread t = new Thread(r);
        // 设置一个有意义的名字
        t.setName(threadNamePrefix + "-" + counter.incrementAndGet());
        // 设置为守护线程
        t.setDaemon(Boolean.TRUE);
        // 设置不同的优先级; 比如我们有多个线程池,分别处理普通任务和紧急任务。
        t.setPriority(Thread.MAX_PRIORITY);
        // 设置某个类的或者自定义的的类加载器
        // t.setContextClassLoader();
        // 设置此线程的最外层异常处理器
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.exit(-1);
            }
        });
        // 不需要启动; 直接返回;
        return t;
    }
}