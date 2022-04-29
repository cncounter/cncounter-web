import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DemoLock
        extends AbstractQueuedSynchronizer
        implements Lock {

    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        // 1. 此处设置断点
        boolean success = lock.tryLock();
        // 1.1 此处设置断点
        lock.lock();
        //
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 2. 此处设置断点
                lock.lock();
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 2.1 此处设置断点
                lock.unlock();
            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 1.2 此处设置断点
        lock.unlock();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        super.acquire(1);
    }

    @Override
    public void unlock() {
        if (Thread.currentThread()
                != super.getExclusiveOwnerThread())
            throw new IllegalMonitorStateException();
        super.release(1);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, null, null, null);
        executor.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
        executor.setCorePoolSize(1);
    }

    @Override
    public boolean tryLock() {
        // 当前线程持有; 不存在同步问题;
        if (Thread.currentThread()
                == super.getExclusiveOwnerThread()) {
            setState(getState() + 1);
            return true;
        }
        // ...
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return new ConditionObject();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        super.acquireInterruptibly(1);
    }
}
