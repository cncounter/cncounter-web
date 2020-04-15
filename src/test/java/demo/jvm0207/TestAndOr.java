package demo.jvm0207;

// 测试短路与或
public class TestAndOr {
    public static void main(String[] args) {
        boolean result = get("exp1", false) || get("exp2", true) && get("exp3", false);
    }

    private static boolean get(String name, boolean result) {
        System.out.println(name + ".will.return:" + result);
        return result;
    }
/*
exp1.will.return:false
exp2.will.return:true
exp3.will.return:false
 */
}
