package selib;

import java.util.HashMap;
import java.util.Random;

public class TestHashMapO {

    public int id;

    public TestHashMapO(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public static void main(String[] args) {
        int num = 100_0000;
        int randomNum = new Random().nextInt(num);
        TestHashMapO randomKey = null;
        //
        long startMillis = System.currentTimeMillis();
        HashMap<TestHashMapO, Integer> map = new HashMap<>();
        for (int i = 0; i < num; i++) {
            map.put(new TestHashMapO(i), i);
            map.put(new TestHashMapO(i), i);
            map.put(new TestHashMapO(i), i);
            TestHashMapO key = new TestHashMapO(i);
            if (randomNum == i) {
                randomKey = key;
            }
            map.put(key, i);
        }
        long endMillis = System.currentTimeMillis();
        System.out.println("填充数量" + num +
                "; 耗时:" + (endMillis - startMillis) + "ms;");
        //
        Integer value = map.get(randomKey);
        System.out.println(value);
        //
    }
}
