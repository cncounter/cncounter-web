package lock;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

    public static volatile AtomicBoolean testFlag = null;
    //



    public static void main(String[] args) throws Exception {
        // t1
        testFlag = new AtomicBoolean();
        // t2
        testFlag = new AtomicBoolean();

        //

        //lockAwait();
        // syncWait();
        //
        final AtomicBoolean flag = new AtomicBoolean(false);

        //
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行结束:" + Thread.currentThread().getName());
                flag.set(true);
            }
        });
        t1.start();

        //
        try {
            // t1.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // load, aload;
        while (!flag.get()) {
            try {
                System.out.println("=======" + Thread.currentThread().getName());
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //
        System.out.println("执行结束:" + Thread.currentThread().getName());

        // Lock
        // Condition
        //
        Future future = null;
        future.get();
        future.get(10, TimeUnit.SECONDS);

    }


    public static void lockAwait() {
        Lock lock = new ReentrantLock(true);
        //
        // lock.lock();
        // boolean success = lock.tryLock();
        try {
            // 错误的用法
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=====");
    }

    public static void syncWait() {
        Object lock = new Object();
        //
        lock.notify();

        // 等价于 lock.lock();
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.notify();
        }
        System.out.println("=====");
    }
}
