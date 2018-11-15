package com.cncounter.test.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @see @link{http://hg.openjdk.java.net/code-tools/jol/file/03064c057dc9/jol-samples/src/main/java/org/openjdk/jol/samples/JOLSample_03_Packing.java}
 */
public class JOLSample_03_Packing {

    /*
     * 演示JVM如何压缩(pack)/排列各个字段.
     *
     * JVM会调整各种类型字段出现的顺序,让其紧凑, 以减少内存占用(memory footprint).
     * 执行示例代码, 可以看到各种字段被紧凑地(densely)排列(packed)在一起,
     * 让字段间的间隙最小化。 排列的大体顺序是 8->4->2->1;
     * 使用这样的排列规则, 比如先排列8字节的字段时,
     * 如果需要对齐, 可以用后面较小的字段来尽量占满空隙。
     *
     * 请注意: 实际内存中的字段顺序, 和代码中声明的字段顺序很可能不一致。
     *
     * 在JVM规范文档中并没有这种要求?? (这属于特定实现的优化, 如 OpenJDK;)
     *
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
        boolean bo1, bo2;
        byte b1, b2;
        char c1, c2;
        double d1, d2;
        float f1, f2;
        int i1, i2;
        long l1, l2;
        short s1, s2; //, s3; // 测试时, 随便加一些字段试试;
    }

}