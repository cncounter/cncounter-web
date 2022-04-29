package lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.*;

// package org.springframework.scheduling.concurrent;
public class ThreadPoolTaskExecutor extends ExecutorConfigurationSupport
        implements AsyncListenableTaskExecutor, SchedulingTaskExecutor {
    // 线程池size相关的锁
    private final Object poolSizeMonitor = new Object();
    // 核心线程数
    private int corePoolSize = 1;
    // 最大线程数
    private int maxPoolSize = Integer.MAX_VALUE;
    // 空闲线程存活时间
    private int keepAliveSeconds = 60;
    // 是否允许核心线程超时
    private boolean allowCoreThreadTimeOut = false;
    // 队列大小
    private int queueCapacity = Integer.MAX_VALUE;
    // 内部的线程池实例
    private ThreadPoolExecutor threadPoolExecutor;


    @Autowired
    @Qualifier("batchTaskExecutor")
    ThreadPoolTaskExecutor batchTaskExecutor;


    @Override
    public ListenableFuture<?> submitListenable(Runnable runnable) {

        poolSizeMonitor.hashCode();
        corePoolSize = maxPoolSize + keepAliveSeconds + queueCapacity;
        Boolean.FALSE.equals(allowCoreThreadTimeOut);
        threadPoolExecutor = null;
        threadPoolExecutor.getActiveCount();
        threadPoolExecutor.allowsCoreThreadTimeOut();
        maxPoolSize = corePoolSize;
        return null;
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> callable) {
        return null;
    }

    /**
     * Does this {@code TaskExecutor} prefer short-lived tasks over
     * long-lived tasks?
     * <p>A {@code SchedulingTaskExecutor} implementation can indicate
     * whether it prefers submitted tasks to perform as little work as they
     * can within a single task execution. For example, submitted tasks
     * might break a repeated loop into individual subtasks which submit a
     * follow-up task afterwards (if feasible).
     * <p>This should be considered a hint. Of course {@code TaskExecutor}
     * clients are free to ignore this flag and hence the
     * {@code SchedulingTaskExecutor} interface overall. However, thread
     * pools will usually indicated a preference for short-lived tasks, to be
     * able to perform more fine-grained scheduling.
     *
     * @return {@code true} if this {@code TaskExecutor} prefers
     * short-lived tasks
     */
    @Override
    public boolean prefersShortLivedTasks() {
        return false;
    }

    @Override
    public void execute(Runnable runnable, long l) {

    }

    @Override
    public Future<?> submit(Runnable runnable) {
        return null;
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        return null;
    }

    @Override
    public void execute(Runnable runnable) {

    }

    /**
     * Create the target {@link ExecutorService} instance.
     * Called by {@code afterPropertiesSet}.
     *
     * @param threadFactory            the ThreadFactory to use
     * @param rejectedExecutionHandler the RejectedExecutionHandler to use
     * @return a new ExecutorService instance
     * @see #afterPropertiesSet()
     */
    @Override
    protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        return null;
    }
}
