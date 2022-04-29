package jit;

/*
请使用JVM启动参数:

-server -XX:+PrintCompilation -XX:CompileThreshold=450
-XX:+PrintSafepointStatistics
-XX:-UseBiasedLocking -XX:-UseCounterDecay

 */
public class TestCompileThreshold {
    public static void main(String[] args) {
        System.out.println("before testCompile()...");
        int num = 3800;
        for (int i = 0; i < num; i++) {
            testCompile();
        }
        System.out.println("after testCompile()...");
    }

    private static void testCompile() {
        System.out.print("");
    }
}
