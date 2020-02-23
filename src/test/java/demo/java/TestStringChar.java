package demo.java;

import java.lang.reflect.Field;

public class TestStringChar {

    public static void main(String[] args) throws Exception {
        //
        String aaaObj = new String("AAA");
        Class<String> aaaClass = String.class;
        // java.lang.NoSuchFieldException: value
        // Field valueField = aaaClass.getField("value");
        Field valueField = aaaClass.getDeclaredField("value");
        valueField.setAccessible(Boolean.TRUE);
        //
        char[] aaaCharArray = (char[]) valueField.get(aaaObj);
        aaaCharArray[0] = 'B';
        // 我们都使用过 javac, 请思考为什么?
        System.out.println(aaaObj); // BAA
        System.out.println("AAA");  // BAA

    }
}
