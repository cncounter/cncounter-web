import com.alibaba.fastjson.JSON;
import org.openjdk.jol.vm.VM;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 演示静态变量
 */
public class DemoStaticField {
    public static int count = 10;

    public static void main(String[] args) throws NoSuchFieldException {
        Unsafe unsafe = getUnsafe();
        Class clazz = DemoStaticField.class;
        //
        Field field_count = clazz.getDeclaredField("count");
        //
        Object field_count_base = unsafe.staticFieldBase(field_count);
        System.out.println("field_count_base=" + field_count_base);
        System.out.println("field_count_base.getClass()=" + field_count_base.getClass());
        Class field_count_base_class = (Class) field_count_base;
        System.out.println("field_count_base_class.simpleName=" + field_count_base_class.getSimpleName());
        //
        long field_count_offset = unsafe.staticFieldOffset(field_count);
        System.out.println("field_count_offset=" + field_count_offset);
        //
        long field_count_addr = 0;//unsafe.getAddress(field_count_offset);
        // System.out.println("field_count_addr=" + field_count_addr);
        int field_count_value = unsafe.getInt(clazz, field_count_offset);
        System.out.println("field_count_value=" + field_count_value);

        long clazzAddr = VM.current().addressOf(clazz);
        System.out.println("clazzAddr=" + toHex(clazzAddr));
        //
        JSON.toJavaObject(null, null);
        ThreadPoolTaskExecutor executor;
    }


    @Bean(name = "batchTaskExecutor")
    public ThreadPoolTaskExecutor batchTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor =
                new ThreadPoolTaskExecutor();
        //设置线程池参数信息
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(8);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setKeepAliveSeconds(600);
        taskExecutor.setThreadNamePrefix("batchExecutor-");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(false);
        taskExecutor.setAwaitTerminationSeconds(5);
        //修改拒绝策略为使用当前线程执行
        taskExecutor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化线程池
        taskExecutor.initialize();
        return taskExecutor;
    }


    public static String toHex(long value) {
        return Long.toHexString(value);
    }

    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe instance = (Unsafe) field.get(null);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
