package com.cncounter.test.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @see @link{http://hg.openjdk.java.net/code-tools/jol/file/03064c057dc9/jol-samples/src/main/java/org/openjdk/jol/samples/JOLSample_02_Alignment.java}
 */
public class JOLSample_02_Alignment {

    /**
     * 演示内部对齐:
     *
     * 为了保证性能(performance)和正确性(correctness), 底层的硬件平台会要求对齐(align)。
     * 也就是说, 字段的起始位置要和其长度对齐；
     *   4字节属性(int, float)就必须从4的整数倍的地方开始对齐。
     *   8字节属性(long, double)就必须从8的整数倍的地方开始对齐。
     *   boolean 值因为只占用1个字节,所以不会占用内部对齐的空间。
     *   因为 long 需要对齐到8的整数倍, 有时候就会在对象头后面造成空白。
     */

    public static void main(String[] args) throws Exception {
        // JVM基本信息
        String vmDetails = VM.current().details();
        //
        ClassLayout demoClassLayout = ClassLayout.parseClass(DemoClass.class);
        //
        System.out.println("====================================");
        System.out.println(vmDetails);
        System.out.println("====================================");
        System.out.println(demoClassLayout.toPrintable());
    }

    public static class DemoClass {
        long ts;
        // 测试时可以放开这个注释; 看看有什么不同;
        // int age;
    }

}
