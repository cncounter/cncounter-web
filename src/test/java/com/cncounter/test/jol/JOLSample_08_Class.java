package com.cncounter.test.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @see @link{http://hg.openjdk.java.net/code-tools/jol/file/tip/jol-samples/src/main/java/org/openjdk/jol/samples/}
 */
public class JOLSample_08_Class {


    /**
     * 演示JVM中需要特殊处理的另外一些字段.
     *
     * 运行本示例, 可以看到:
     * 在实例的属性字段部分, 存在大量的空隙。
     * 这些空白之处, 没有对应的Java字段，不像Exception的示例中存在“隐藏”字段。
     * 原因在于，JVM会“注入”一些东西到Class中，需要用这些空白来存储一些元数据信息。
     *
     *
     *
     * 另请参见:
     *  http://hg.openjdk.java.net/jdk8/jdk8/hotspot/file/tip/src/share/vm/classfile/javaClasses.hpp
     *  http://hg.openjdk.java.net/jdk8/jdk8/hotspot/file/tip/src/share/vm/classfile/javaClasses.cpp
     */

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println(VM.current().details());
        System.out.println("====================================");
        System.out.println(ClassLayout.parseClass(Class.class).toPrintable());
    }

}
