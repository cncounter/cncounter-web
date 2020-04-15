package com.cncounter.test.jol;

import org.openjdk.jol.info.ClassLayout;

public class MyOrder {
    private long orderId;
    private long userId;
    private byte state;
    private long createMillis;

    public static void main(String[] args) {
        // JVM基本信息
        ClassLayout demoClassLayout = ClassLayout.parseClass(MyOrder.class);
        //
        System.out.println("====================================");
        System.out.println(demoClassLayout.toPrintable());
    }
}