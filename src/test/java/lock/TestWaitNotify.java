package lock;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class TestWaitNotify {


    public static void main(String[] args) {

        Thread threadMain = Thread.currentThread();
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1L);
                    threadMain.interrupt();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Integer lock1 = new Integer(1);
        tryWait(lock1);
        tryNotify(lock1);
        System.out.println(":::before::synchronized::1");
        synchronized (lock1) {
            tryNotify(lock1);
            tryWait(lock1);
        }
        System.out.println(":::after::synchronized::1");
        synchronized (lock1) {
            tryWait(lock1);
            tryNotify(lock1);
        }
        System.out.println(":::after::synchronized::2");

    }

    public static boolean tryWait(Object lock) {
        try {
            System.out.println("::before:::lock.wait()");
            lock.wait(100);
            System.out.println("::after:::lock.wait()");
            return true;
        } catch (IllegalMonitorStateException e) {
            System.out.println("!!! wait() 只能在当前线程拥有该lock时调用");
            return false;
        } catch (InterruptedException e) {
            System.out.println("!!! wait() 期间该线程被打断");
            return false;
        }
    }

    public static boolean tryNotify(Object lock) {
        try {
            System.out.println("==before:::lock.notify()");
            lock.notify();
            System.out.println("==after:::lock.notify()");
            return true;
        } catch (IllegalMonitorStateException e) {
            System.out.println("!!! notify() 只能在当前线程拥有该lock时调用");
            return false;
        }
    }
}
