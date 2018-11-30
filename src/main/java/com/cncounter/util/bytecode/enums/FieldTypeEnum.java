package com.cncounter.util.bytecode.enums;

/**
 * 字段类型
 */
public enum FieldTypeEnum {
    ByteType("B", "byte", "B"),
    CharType("C", "char", "C"),
    DoubleType("D", "double", "D"),
    FloatType("F", "float", "F"),
    IntType("I", "int", "I"),
    LongType("J", "long", "J"),
    ClassType("L", "reference", "L[^;]*;"),
    ShortType("S", "short", "S"),
    BooleanType("Z", "boolean", "Z"),
    PrimitiveArrayType("[", "array", "\\[.*");

    //开始字符
    public String term; // 术语
    public String type; // 类型说明
    public String pattern; // 模式

    FieldTypeEnum(String term, String type, String pattern) {
        this.term = term;
        this.type = type;
        this.pattern = pattern;
    }
}

/*
Table 4.3-A. Interpretation of field descriptors

| FieldType term    | Type        | Interpretation                           |
| ------------------- | ----------- | ---------------------------------------- |
|  B                  |  byte       | signed byte                              |
|  C                  |  char       | Unicode character code point in the Basic Multilingual Plane, encoded with UTF-16 |
|  D                  |  double     | double-precision floating-point value    |
|  F                  |  float      | single-precision floating-point value    |
|  I                  |  int        | integer                                  |
|  J                  |  long       | long integer                             |
|  L  ClassName  ;  |  reference  | an instance of class ClassName         |
|  S                  |  short      | signed short                             |
|  Z                  |  boolean    |  true  or  false                         |
|  [                  |  reference  | one array dimension                      |
*/
