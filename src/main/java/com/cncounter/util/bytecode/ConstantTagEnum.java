package com.cncounter.util.bytecode;


// 常量标签
public enum ConstantTagEnum{
    CONSTANT_Utf8(1),
    CONSTANT_Integer(3),
    CONSTANT_Float(4),
    CONSTANT_Long(5),
    CONSTANT_Double(6),
    CONSTANT_Class(7),
    CONSTANT_String(8),
    CONSTANT_Fieldref(9),
    CONSTANT_Methodref(10),
    CONSTANT_InterfaceMethodref(11),
    CONSTANT_NameAndType(12),
    CONSTANT_MethodHandle(15),
    CONSTANT_MethodType(16),
    CONSTANT_InvokeDynamic(18);

    public final int tag;

    ConstantTagEnum(int tag) {
        this.tag = tag;
    }

    public static ConstantTagEnum parseByTag(int tag){
        //
        ConstantTagEnum[] allEnum = ConstantTagEnum.class.getEnumConstants();
        for(ConstantTagEnum e : allEnum){
            if(e.tag == tag){
                return e;
            }
        }
        return null;
    }
}

/*
Table 4.4-A. Constant pool tags

Constant Type	Value
CONSTANT_Class	7
CONSTANT_Fieldref	9
CONSTANT_Methodref	10
CONSTANT_InterfaceMethodref	11
CONSTANT_String	8
CONSTANT_Integer	3
CONSTANT_Float	4
CONSTANT_Long	5
CONSTANT_Double	6
CONSTANT_NameAndType	12
CONSTANT_Utf8	1
CONSTANT_MethodHandle	15
CONSTANT_MethodType	16
CONSTANT_InvokeDynamic	18

*/