package jvm.chapter03;

public class SynchronizedAdder {
    private int sum = 0;

    public synchronized int incrementAndGet() {
        return ++sum;
    }

    public int addAndGet() {
        synchronized (this) {
            return ++sum;
        }
    }
}
