package com.cncounter.util.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.*;
import java.util.*;

/**
 * JVM相关工具
 */
public class JVMUtils {

    /**
     * 线程转储
     *
     * @return
     */
    public static String snapThreadDump() {
        StringBuffer threadDump = new StringBuffer("\n"); //(System.lineSeparator());
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        for (ThreadInfo threadInfo : threadMXBean.dumpAllThreads(true, true)) {
            threadDump.append(threadInfo.toString());
        }
        return threadDump.toString();
    }

    /**
     * 当前JVM进程的pid
     *
     * @return
     */
    public static long currentPid() {
        //
        final long fallback = -1;
        //
        final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        final int index = jvmName.indexOf("@");

        if (index < 1) {
            return fallback;
        }
        String pid = jvmName.substring(0, index);
        if (null != pid && pid.matches("\\d+")) {
            return Long.parseLong(pid);
        }
        return fallback;
    }

    /**
     * JVM 信息
     * @return
     */
    public static String jvmInfo(){
        //
        String systemFlags = getSystemFlags();
        //
        return systemFlags;
    }


    /**
     * 获取JVM对象统计信息
     *
     * @param limit 限制的 topN 数量
     * @return
     */
    public static String histogram(final int limit) {
        return histogram(limit, null, null);
    }

    public static String histogram(final int limit, List<String> includes, List<String> excludes) {
        if (null == includes) {
            includes = new ArrayList<String>();
        }
        if (null == excludes) {
            excludes = new ArrayList<String>();
        }
        //
        long pid = currentPid();
        //
        String jmapCommandName = "jmap";
        File jmapFile = findJmap();
        if (null != jmapFile && jmapFile.isFile()) {
            jmapCommandName = jmapFile.getAbsolutePath();
        }
        //
        List<String> cmdArgsList = new ArrayList<String>();
        cmdArgsList.add(jmapCommandName);
        cmdArgsList.add("-histo");
        cmdArgsList.add("" + pid);
        //
        String[] cmdToRunWithArgs = cmdArgsList.toArray(new String[cmdArgsList.size()]);
        //
        List<String> result = runNative(cmdToRunWithArgs);
        if (null == result) {
            return "";
        }
        //
        int counter = 0;
        //
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            String line = result.get(i);
            if (containsListItem(line, excludes)) {
                // 在排除列表
                continue;
            } else if (containsListItem(line, includes)) {
                // 在包含列表
            } else if (limit < 1) {
                // 未限制
            } else if (i + 1 == result.size()) {
                // 最后一行
            } else if (counter >= limit+3) {
                continue; // 超过限制则跳过
            }
            builder.append(line);
            builder.append("\n");
            counter ++;
        }
        //
        return builder.toString();
    }

    // 包含list元素
    private static boolean containsListItem(String line, List<String> stringList) {
        for (String item : stringList) {
            if (line.contains(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 执行shell命令，并返回多行结果
     *
     * @param cmdToRunWithArgs 执行命令的数组
     * @return 失败则返回空集合
     */
    public static List<String> runNative(String[] cmdToRunWithArgs) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmdToRunWithArgs);
        } catch (SecurityException e) {
            return new ArrayList<String>(0);
        } catch (IOException e) {
            return new ArrayList<String>(0);
        }

        ArrayList<String> sa = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sa.add(line);
            }
            p.waitFor();
        } catch (IOException e) {
            return new ArrayList<String>(0);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        } finally {
            IOUtils.close(reader);
        }
        return sa;
    }


    // 查找jmap命令的位置
    private static File findJmap() {
        // Try to find jmap under java.home and System env JAVA_HOME
        String javaHome = System.getProperty("java.home");
        String[] paths = {"bin/jmap", "bin/jmap.exe", "../bin/jmap", "../bin/jmap.exe"};

        List<File> jmapList = new ArrayList<File>();
        for (String path : paths) {
            File jmapFile = new File(javaHome, path);
            if (jmapFile.exists()) {
                jmapList.add(jmapFile);
            }
        }

        if (jmapList.isEmpty()) {
            String javaHomeEnv = System.getenv("JAVA_HOME");
            for (String path : paths) {
                File jmapFile = new File(javaHomeEnv, path);
                if (jmapFile.exists()) {
                    jmapList.add(jmapFile);
                }
            }
        }

        if (jmapList.isEmpty()) {
            return null;
        }

        // find the shortest path, jre path longer than jdk path
        if (jmapList.size() > 1) {
            Collections.sort(jmapList, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    try {
                        return file1.getCanonicalPath().length() - file2.getCanonicalPath().length();
                    } catch (IOException e) {
                        // ignore
                    }
                    return -1;
                }
            });
        }
        return jmapList.get(0);
    }


    private static String getSystemFlags() {
        //
        Map<String, Object> sysFlags = new HashMap<String, Object>();

        // 运行时信息
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        Map<String, Object> runtimeConfig = new HashMap<String, Object>();
        runtimeConfig.put("vmId", runtimeMXBean.getName());
        runtimeConfig.put("vmName", runtimeMXBean.getVmName());
        runtimeConfig.put("vmVendor", runtimeMXBean.getVmVendor());
        runtimeConfig.put("vmVersion", runtimeMXBean.getVmVersion());
        runtimeConfig.put("specVersion", runtimeMXBean.getSpecVersion());
        runtimeConfig.put("uptime", runtimeMXBean.getUptime());
        runtimeConfig.put("inputArguments", runtimeMXBean.getInputArguments());
        sysFlags.put("runtimeConfig", runtimeConfig);
        // 操作系统
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        Map<String, Object> osConfig = new HashMap<String, Object>();
        osConfig.put("name", osBean.getName());
        osConfig.put("arch", osBean.getArch());
        osConfig.put("version", osBean.getVersion());
        osConfig.put("availableProcessors", osBean.getAvailableProcessors());
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOSBean = ((com.sun.management.OperatingSystemMXBean) osBean);
            osConfig.put("totalPhysicalMemorySize", mb(sunOSBean.getTotalPhysicalMemorySize()));
            osConfig.put("freePhysicalMemorySize", mb(sunOSBean.getFreePhysicalMemorySize()));
        }
        sysFlags.put("osConfig", osConfig);
        //
        return JSON.toJSONString(sysFlags);
    }

    private static String mb(Number num) {
        long mbValue = num.longValue() / (1024 * 1024);
        if (mbValue < 1) {
            return "" + mbValue;
        }
        return mbValue + "MB";
    }
}
