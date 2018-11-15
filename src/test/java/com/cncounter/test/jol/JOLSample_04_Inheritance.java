package com.cncounter.test.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @see @link{http://hg.openjdk.java.net/code-tools/jol/file/03064c057dc9/jol-samples/src/main/java/org/openjdk/jol/samples/JOLSample_04_Inheritance.java}
 */
public class JOLSample_04_Inheritance {

    /**
     * 演示多个类继承时, JVM如何进行布局。
     *
     * JVM需要维持不变的规则是, 不管通过哪个类来访问,
     * 一个类的某个字段, 到起始位置的偏移量必须固定不变。
     * 也就是说, 子类的字段, 必须排列在父类的所有字段之后。
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
        int first;
    }

    public static class ParentDemo extends GrandDemo {
        int second;
    }

    public static class DemoClass extends ParentDemo {
        int third;
    }

}

