package com.cncounter.test.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 */
public class JOLSample_09_Array {


    public static void main(String[] args) throws Exception {
        //
        int[] arrays = new int[5];
        // JVM基本信息
        ClassLayout demoClassLayout = ClassLayout.parseInstance(arrays);
        //
        System.out.println("====================================");
        System.out.println(demoClassLayout.toPrintable());
    }

}
