package demo.java;

import com.alibaba.fastjson.JSON;

/**
 * 测试构造函数泄漏
 */
public class TestConstructLeak {
    public static TestConstructLeak instance;
    private final int num; // 如果Java不指定一个默认值。。。

    public TestConstructLeak(int num){
        if(null == instance){
            instance = this;
        }
        if(num < 0){
            throw new RuntimeException("测试构造函数泄漏");
        }
        this.num = num;

    }

    public int getNum() {
        return num;
    }

    public static void main(String[] args) {
        try{
            new TestConstructLeak(-1);
        } catch (Exception e){
            //
            e.printStackTrace();
        }
        System.out.println(JSON.toJSONString(instance));
    }
}
