package com.cncounter.test.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @see @link{http://hg.openjdk.java.net/code-tools/jol/file/03064c057dc9/jol-samples/src/main/java/org/openjdk/jol/samples/JOLSample_05_InheritanceBarrier.java}
 */
public class JOLSample_05_InheritanceBarrier {

    /**
     * 演示继承屏障;
     * 多个类继承时, HotSpot的特殊行为(建议使用 64-bit JVM)。
     *
     * 可以看到, 虽然 GrandDemo.first 前面有空隙, 但 HotSpot 也不使用,
     * 因为在布局子类的属性时, 不会去父类的地盘中去找空位。
     * 这就造成了 继承屏障(inheritance barrier), 位于 super- 和 sub-class 的属性域之间。
     * 另请参见:
     *    https://bugs.openjdk.java.net/browse/JDK-8024913
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

    public static class GrandDemo {
        long first;
    }

    public static class ParentDemo extends GrandDemo {
        long second;
    }

    public static class DemoClass extends ParentDemo {
        long third;
        int fourth;
    }

}
