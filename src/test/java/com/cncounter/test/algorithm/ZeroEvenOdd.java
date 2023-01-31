package com.cncounter.test.algorithm;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/*
1116. 打印零与奇偶数

本实现主要是演示了 Condition 的用法

https://leetcode.cn/problems/print-zero-even-odd/

 */
public class ZeroEvenOdd {

    private int n;
    private Lock lock = new ReentrantLock();
    private Condition conditionZero = lock.newCondition();
    private Condition conditionOdd = lock.newCondition();
    private Condition conditionEven = lock.newCondition();
    private volatile AtomicInteger currentFlag = new AtomicInteger(0);
    private int flagZero = 0;
    private int flagOdd = 1;
    private int flagEven = 2;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        for (int i = 1; i <= n; i++) {
            if (flagZero != currentFlag.get()) {
                conditionZero.await();
            }
            printNumber.accept(0);
            if (0 == i % 2) {
                currentFlag.compareAndSet(flagZero, flagEven);
                conditionEven.signal();
            } else {
                currentFlag.compareAndSet(flagZero, flagOdd);
                conditionOdd.signal();
            }
        }
        lock.unlock();
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        for (int i = 2; i <= n; i += 2) {
            if (flagEven != currentFlag.get()) {
                conditionEven.await();
            }
            printNumber.accept(i);
            currentFlag.compareAndSet(flagEven, flagZero);
            conditionZero.signal();
        }
        lock.unlock();
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        for (int i = 1; i <= n; i += 2) {
            if (flagOdd != currentFlag.get()) {
                conditionOdd.await();
            }
            printNumber.accept(i);
            currentFlag.compareAndSet(flagOdd, flagZero);
            conditionZero.signal();
        }
        lock.unlock();
    }
}
