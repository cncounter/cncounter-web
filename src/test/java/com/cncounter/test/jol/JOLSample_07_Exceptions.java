package com.cncounter.test.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @see @link{http://hg.openjdk.java.net/code-tools/jol/file/tip/jol-samples/src/main/java/org/openjdk/jol/samples/}
 */
public class JOLSample_07_Exceptions {


    /**
     * 演示JVM中需要特殊处理的一些字段.
     *
     * 比如 Throwable 类中的 backtrace 属性,
     * 查看源码可以发现, 这个字段设置了 transient 标识, 所以在内存 dump 时并没有导出.
     * 因为这个字段持有的是JVM内部的信息，在任何情况下, 用户都不应该访问这些信息。
     *
     * 另请参见:
     *    http://bugs.openjdk.java.net/browse/JDK-4496456
     */

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println(VM.current().details());
        System.out.println("====================================");
        System.out.println(ClassLayout.parseClass(Throwable.class).toPrintable());
    }

}

