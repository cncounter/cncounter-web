package demo.jvm0205;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 线程转储示例
 */
public class JMXDumpThreadDemo {

    public static void main(String[] args) {
        String threadDump = snapThreadDump();
        System.out.println("=================");
        System.out.println(threadDump);
    }

    public static String snapThreadDump() {
        StringBuffer threadDump = new StringBuffer(System.lineSeparator());
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        for (ThreadInfo threadInfo : threadMXBean.dumpAllThreads(true, true)) {
            threadDump.append(threadInfo.toString());
        }
        return threadDump.toString();
    }
}
