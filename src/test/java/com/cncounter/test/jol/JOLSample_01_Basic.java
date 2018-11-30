package com.cncounter.test.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @see @link{http://hg.openjdk.java.net/code-tools/jol/file/03064c057dc9/jol-samples/src/main/java/org/openjdk/jol/samples/JOLSample_01_Basic.java}
 */
public class JOLSample_01_Basic {

    /**
     * 基本示例:
     * 演示基本的字段布局(basic field layout).
     * 请观察以下情况:
     *   1. 对象头占用多少空间;
     *   2. 看看各字段在内存中的排列顺序; 可能和代码中的顺序不一样。
     *   3. 外部对齐占用的空间;
     *   4. 自己增加一些字段试试。
     */

    public static void main(String[] args) {
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

    // 演示的模型类; 可随意设置
    public static class DemoClass {
        private boolean ok;
        private int age;

        public boolean isOk() {
            return this.ok;
        }

        public void setOk(boolean ok) {
            this.ok = ok;
        }
    }

}