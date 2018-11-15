package com.cncounter.test.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @see @link{http://hg.openjdk.java.net/code-tools/jol/file/tip/jol-samples/src/main/java/org/openjdk/jol/samples/}
 */
public class JOLSample_06_Gaps {

    /**
     * 演示HotSpot的另一个特殊行为
     * 多个类继承时, 引起人造的空隙
     *
     * HotSpot将实例的属性域空间向上凑整到引用大小;
     * 但最后的地方会有造成空隙。
     *
     * 另请参见:
     *     https://bugs.openjdk.java.net/browse/JDK-8024912
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
        boolean first;
    }

    public static class ParentDemo extends GrandDemo {
        boolean second;
    }

    public static class DemoClass extends ParentDemo {
        boolean third;
    }

}

