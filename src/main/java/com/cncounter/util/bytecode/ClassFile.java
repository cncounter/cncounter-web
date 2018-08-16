package com.cncounter.util.bytecode;

/**
 * 类文件结构体
 */
public abstract class ClassFile {
    public byte[] magic; //             u4; 魔数
    public byte[] minorVersion; //      u2; 次版本号
    public byte[] majorVersion; //      u2; 主版本号
    public byte[] constantPoolCount; // u2; 常量个数+1
    public byte[] constantPool; //        ; 常量池; 个数 constantPoolCount-1
    public byte[] accessFlags; //       u2; 访问标识
    public byte[] thisClass; //         u2; 本类索引
    public byte[] superClass; //        u2; 父类索引
    public byte[] interfacesCount; //   u2; 接口数量
    public byte[] interfaces; //          ; 接口内容; 个数 interfacesCount
    public byte[] fieldsCount; //       u2; 字段数量
    public byte[] fields; //              ; 字段表; 个数 fieldsCount
    public byte[] methodsCount; //      u2; 方法数量
    public byte[] methods; //             ; 方法表; 个数 methodsCount
    public byte[] attributesCount; //   u2; 属性数量
    public byte[] attributes; //          ; 属性表; 个数 attributesCount
}

/*
ClassFile {
    u4             magic;
    u2             minor_version;
    u2             major_version;
    u2             constant_pool_count;
    cp_info        constant_pool[constant_pool_count-1];
    u2             access_flags;
    u2             this_class;
    u2             super_class;
    u2             interfaces_count;
    u2             interfaces[interfaces_count];
    u2             fields_count;
    field_info     fields[fields_count];
    u2             methods_count;
    method_info    methods[methods_count];
    u2             attributes_count;
    attribute_info attributes[attributes_count];
}
*/