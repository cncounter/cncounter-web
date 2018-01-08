package com.cncounter.test.util;

import java.lang.reflect.Field;

/**
 * 属性域工具类
 */
public class FieldUtils {

    /**
     * 反射获取对象某个属性值的方法
     * @param obj 要取值的对象
     * @param fieldName 字段名称
     * @param clazzs 可变参赛; 当前类, 可以通过 obj.getClass() 获取
     * @return
     * @throws IllegalAccessException
     */
    public static Object getObjectField(Object obj, String fieldName, Class<?>... clazzs) throws IllegalAccessException {
        if(null == obj || null==fieldName || fieldName.trim().isEmpty()){
            return null;
        }
        Class<?> clazz = null;
        if(null == clazzs || clazzs.length<1){
            clazz = obj.getClass();
        } else {
            clazz = clazzs[0];
        }
        // 已经到顶
        if(clazz == Object.class){
            return null;
        }
        //
        Field[] clazzFields= clazz.getDeclaredFields();
        for(Field field : clazzFields){
            String fName = field.getName();
            if(fieldName.trim().equals(fName)){
                field.setAccessible(true);// 设置直接可读
                return field.get(obj); // 返回该字段的值
            }
        }
        // 如果没找到. 递归查找 superClass
        Class<?> superClass = clazz.getSuperclass();
        return getObjectField(obj, fieldName, superClass);
    }

}
