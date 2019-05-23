package com.cncounter.test.util.hash;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * 单元测试用例
 */
public class TestHashMap {

    // 初始化Map实例
    private <K, V> Map<K, V> initMapInstance() {
        CNCHashMap<K, V> map = new CNCHashMap<K, V>();
        return map;
    }


    // 测试存进去值
    @Test
    public void testPut() {
        //
        Map<String, String> map = initMapInstance();
        //
        String key1 = "url";
        String value1 = "www.cncounter.com";
        //
        map.put(key1, value1);
        // 只要不报错就行
    }

    // 测试存进去的值和取出来的值相等
    @Test
    public void testPutAndGet() {
        //
        Map<String, String> map = initMapInstance();
        //
        String key1 = "url";
        String value1 = "www.cncounter.com";
        String key2 = "url2";
        String value2 = "www.cncounter.com";
        //
        map.put(key1, value1);
        map.put(key2, value2);
        //
        String v1 = map.get(key1);
        String v2 = map.get(key2);
        //
        Assert.assertNotNull("get返回值不能是NULL", v1);
        Assert.assertNotNull("get返回值不能是NULL", v2);
        //
        Assert.assertEquals("get返回值必须等于存进去的值", value1, v1);
        Assert.assertEquals("get返回值必须等于存进去的值", value2, v2);
        //
        System.out.println("===================");
        System.out.println(map.toString());

    }

    @Test
    public void testSize() {
        //
        Map<String, String> map = initMapInstance();
        //
        String key1 = "url";
        String value1 = "www.cncounter.com";
        //
        map.put(key1, value1);
        //
        int size = map.size();
        //
        Assert.assertTrue("有key之后size不能是0", size > 0);
        Assert.assertEquals("size不能出问题", 1, size);
    }

    @Test
    public void testSize2() {
        //
        Map<String, String> map = initMapInstance();
        //
        String key1 = "url";
        String value1 = "www.cncounter.com";
        String key2 = "url2";
        String value2 = "www.cncounter.com";
        //
        map.put(key1, value1);
        map.put(key2, value2);
        //
        int size = map.size();
        //
        Assert.assertTrue("有key之后size不能是0", size > 0);
        Assert.assertEquals("size不能出问题", 2, size);
    }

    @Test
    public void testSameKeySize() {
        //
        Map<String, String> map = initMapInstance();
        //
        String key1 = "url";
        String value1 = "www.cncounter.com";
        //
        map.put(key1, value1);
        map.put(key1, value1 + value1);
        map.put(key1, value1 + value1 + value1);
        //
        int size = map.size();
        //
        Assert.assertTrue("有key之后size不能是0", size > 0);
        Assert.assertEquals("同样的key不能出现多个", 1, size);
    }
}
