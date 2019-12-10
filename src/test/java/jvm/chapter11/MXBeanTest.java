package jvm.chapter11;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.lang.management.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MXBeanTest {

    public static void main(String[] args) {
        Map<String, Object> beansMap = loadMXBeanMap();
        String jsonString = toJSON(beansMap);
        System.out.println(jsonString);
    }

    public static Map<String, Object> loadMXBeanMap() {
        // import java.lang.management.*
        // 1. 操作系统信息
        OperatingSystemMXBean operatingSystemMXBean =
                ManagementFactory.getOperatingSystemMXBean();
        // 2. 运行时
        RuntimeMXBean runtimeMXBean =
                ManagementFactory.getRuntimeMXBean();
        // 3.1 JVM内存信息
        MemoryMXBean memoryMXBean =
                ManagementFactory.getMemoryMXBean();
        // 3.2 JVM内存池-列表
        List<MemoryPoolMXBean> memoryPoolMXBeans =
                ManagementFactory.getMemoryPoolMXBeans();
        // 3.3 内存管理器-列表
        List<MemoryManagerMXBean> memoryManagerMXBeans =
                ManagementFactory.getMemoryManagerMXBeans();
        // 4. class加载统计信息
        ClassLoadingMXBean classLoadingMXBean =
                ManagementFactory.getClassLoadingMXBean();
        // 5. 编译统计信息
        CompilationMXBean compilationMXBean =
                ManagementFactory.getCompilationMXBean();
        // 6. 线程
        ThreadMXBean threadMXBean =
                ManagementFactory.getThreadMXBean();
        // 7.GC
        List<GarbageCollectorMXBean> garbageCollectorMXBeans =
                ManagementFactory.getGarbageCollectorMXBeans();
        // 8. 获取平台日志MXBean
        PlatformLoggingMXBean platformLoggingMXBean =
                ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean.class);

        //
        Map<String, Object> beansMap = new HashMap<String, Object>();
        //
        //beansMap.put("operatingSystemMXBean", operatingSystemMXBean);
        //beansMap.put("runtimeMXBean", runtimeMXBean);
        beansMap.put("memoryMXBean", memoryMXBean);
        /*beansMap.put("memoryPoolMXBeans", memoryPoolMXBeans);
        beansMap.put("memoryManagerMXBeans", memoryManagerMXBeans);
        beansMap.put("classLoadingMXBean", classLoadingMXBean);
        beansMap.put("compilationMXBean", compilationMXBean);
        beansMap.put("threadMXBean", threadMXBean);
        beansMap.put("garbageCollectorMXBeans", garbageCollectorMXBeans);
        beansMap.put("platformLoggingMXBean", platformLoggingMXBean);*/
        return beansMap;
    }

    public static String toJSON(Object obj) {
        // MemoryPoolMXBean 这些未设置的属性序列化会报错
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getExcludes().add("collectionUsageThreshold");
        filter.getExcludes().add("collectionUsageThresholdCount");
        filter.getExcludes().add("collectionUsageThresholdExceeded");
        filter.getExcludes().add("usageThreshold");
        filter.getExcludes().add("usageThresholdCount");
        filter.getExcludes().add("usageThresholdExceeded");
        //
        String jsonString = JSON.toJSONString(obj, filter, SerializerFeature.PrettyFormat);
        //
        return jsonString;
    }
}
