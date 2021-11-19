import org.openjdk.jol.vm.VM;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/*
  演示Class对象的内存地址; 证明Class对象分配在Java_Heap中;
  请设置JVM启动参数 -XX:+PrintGCDetails

某次运行结果为(详细日志参见本文件末尾):

clazzAddr = 0x000000076ad2acd0
eden space 65536K, 36% used [0x000000076ab00000,0x000000076c2193e8,0x000000076eb00000)

eden区的大小 65536K, 36%使用率; 起始地址空间76ab00000, 使用到地址76c2193e8, 结束地址为76eb00000

把Eden区的这些数字相减:
0x000000076c2193e8 - 0x000000076ab00000
= 24220648 = Eden区当前使用量为 23.09MB

0x000000076eb00000 - 0x000000076ab00000
=67108864 = Eden区当前总容量为 64MB

0x000000076ad2acd0 - 0x000000076ab00000
= 2272464 = 这个clazz对象距离Eden区起始空间为2MB多一点点

::: 由此可知 clazzAddr 对应的Class对象,位于Eden区中.

*/
public class DemoClassObjMemAddr {

    public static void main(String[] args) {
        // 获取 Class 对象
        Class clazz = DemoClassObjMemAddr.class;
        // 使用JOL工具, 或者对象的地址;
        long clazzAddr = VM.current().addressOf(clazz);
        // 打印该地址
        System.out.println("clazzAddr = " + to16CharHex(clazzAddr));
    }

    public static String to16CharHex(long value) {
        // 16位hex数字
        final int padding = 16;
        String hexString = Long.toHexString(value);
        while (hexString.length() < padding) {
            hexString = "0" + hexString;
        }
        return "0x" + hexString;
    }
}

/*
<!-- JOL工具的相关依赖如下: -->
<!-- https://mvnrepository.com/artifact/org.openjdk.jol/jol-core -->
<dependency>
    <groupId>org.openjdk.jol</groupId>
    <artifactId>jol-core</artifactId>
    <version>0.16</version>
    <scope>provided</scope>
</dependency>
*/

/*
执行结果如下:

clazzAddr = 0x000000076ad2acd0

Heap
 PSYoungGen      total 76288K, used 23652K [0x000000076ab00000, 0x0000000770000000, 0x00000007c0000000)
  eden space 65536K, 36% used [0x000000076ab00000,0x000000076c2193e8,0x000000076eb00000)
  from space 10752K, 0% used [0x000000076f580000,0x000000076f580000,0x0000000770000000)
  to   space 10752K, 0% used [0x000000076eb00000,0x000000076eb00000,0x000000076f580000)
 ParOldGen       total 175104K, used 0K [0x00000006c0000000, 0x00000006cab00000, 0x000000076ab00000)
  object space 175104K, 0% used [0x00000006c0000000,0x00000006c0000000,0x00000006cab00000)
 Metaspace       used 7937K, capacity 8134K, committed 8320K, reserved 1056768K
  class space    used 946K, capacity 1030K, committed 1152K, reserved 1048576K

 */