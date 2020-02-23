package demo.jvm0205;

import java.util.concurrent.TimeUnit;

/**
 * 演示: java.lang.OutOfMemoryError: unable to create new native thread
 */
public class UnableCreateNativeThread implements Runnable {
    public static void main(String[] args) {
        UnableCreateNativeThread task = new UnableCreateNativeThread();
        int i = 0;
        while (true){
            //
            System.out.println("尝试创建: " + (i++));
            // 持续创建线程
            try {
                new Thread(task).start();
            } catch (Throwable e){
                System.exit(0);
            }
        }
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
