package com.cncounter.util.bytecode;

/**
 * 访问标识
 */
public enum AccessFlagsEnum {
    //
    ACC_PUBLIC(0x0001, "public"),
    ACC_PRIVATE(0x0002, "private"),
    ACC_PROTECTED(0x0004, "protected"),
    ACC_STATIC(0x0008, "static"),
    //
    ACC_FINAL(0x0010, "final"),
    // super. 指令调用需要特殊处理(invokespecial)
    ACC_SUPER(0x0020, "[super.]"),
    ACC_VOLATILE(0x0040, "volatile"),
    ACC_TRANSIENT(0x0080, "transient"),
    //
    ACC_INTERFACE(0x0200, "interface"),
    ACC_ABSTRACT(0x0400, "abstract"),
    // 如 <init> 等编译器生成的方法,或泛型类
    ACC_SYNTHETIC(0x1000, "<synthetic>"),
    ACC_ANNOTATION(0x2000, "@interface"),
    ACC_ENUM(0x4000, "enum");

    public int value;
    public String type;

    AccessFlagsEnum(int value, String type) {
        this.value = value;
        this.type = type;
    }



    // 解析标志位
    public static String parseAccessFlags(int accessFlagsNumber){
        String result = "";
        //
        // 可见范围
        if(AccessFlagsEnum.ACC_PUBLIC.in(accessFlagsNumber)){
            result += " " + AccessFlagsEnum.ACC_PUBLIC.type;
        }
        if(AccessFlagsEnum.ACC_PRIVATE.in(accessFlagsNumber)){
            result += " " + AccessFlagsEnum.ACC_PRIVATE.type;
        }
        if(AccessFlagsEnum.ACC_PROTECTED.in(accessFlagsNumber)){
            result += " " + AccessFlagsEnum.ACC_PROTECTED.type;
        }
        //
        if(AccessFlagsEnum.ACC_STATIC.in(accessFlagsNumber)){
            result += " " + AccessFlagsEnum.ACC_STATIC.type;
        }
        if(AccessFlagsEnum.ACC_FINAL.in(accessFlagsNumber)){
            result += " " + AccessFlagsEnum.ACC_FINAL.type;
        }
        if(AccessFlagsEnum.ACC_ABSTRACT.in(accessFlagsNumber)){
            result += " " + AccessFlagsEnum.ACC_ABSTRACT.type;
        }
        if(AccessFlagsEnum.ACC_VOLATILE.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_VOLATILE.type;
        }
        if(AccessFlagsEnum.ACC_TRANSIENT.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_TRANSIENT.type;
        }
        //
        if(AccessFlagsEnum.ACC_ANNOTATION.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_ANNOTATION.type;
        }
        if(AccessFlagsEnum.ACC_ENUM.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_ENUM.type;
        }
        if(AccessFlagsEnum.ACC_INTERFACE.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_INTERFACE.type;
        }
        // 特殊标记
        //
        if(AccessFlagsEnum.ACC_SUPER.in(accessFlagsNumber)){
            //result += " " + AccessFlagsEnum.ACC_SUPER.type;
        }
        if(AccessFlagsEnum.ACC_SYNTHETIC.in(accessFlagsNumber)) {
            //result += " " + AccessFlagsEnum.ACC_SYNTHETIC.type;
        }

        //
        return result;
    }
    /**
     * 拥有标志位
     * @param flags
     * @return
     */
    public boolean in(int flags) {
        if (value == (value & flags)) {
            return true;
        } else {
            return false;
        }
    }
}



/*

# 类访问修饰
Table 4.1-A. Class access and property modifiers

| Flag Name        | Value  | Interpretation                           |
| ---------------- | ------ | ---------------------------------------- |
|  ACC_PUBLIC      | 0x0001 | Declared  public; may be accessed from outside its package. |
|  ACC_FINAL       | 0x0010 | Declared  final; no subclasses allowed.  |
|  ACC_SUPER       | 0x0020 | Treat superclass methods specially when invoked by the invokespecial instruction. |
|  ACC_INTERFACE   | 0x0200 | Is an interface, not a class.            |
|  ACC_ABSTRACT    | 0x0400 | Declared  abstract; must not be instantiated. |
|  ACC_SYNTHETIC   | 0x1000 | Declared synthetic; not present in the source code. |
|  ACC_ANNOTATION  | 0x2000 | Declared as an annotation type.          |
|  ACC_ENUM        | 0x4000 | Declared as an  enum  type.              |



# 字段访问修饰
Table 4.5-A. Field access and property flags

| Flag Name       | Value  | Interpretation                           |
| --------------- | ------ | ---------------------------------------- |
|  ACC_PUBLIC     | 0x0001 | Declared  public ; may be accessed from outside its package. |
|  ACC_PRIVATE    | 0x0002 | Declared  private ; usable only within the defining class. |
|  ACC_PROTECTED  | 0x0004 | Declared  protected ; may be accessed within subclasses. |
|  ACC_STATIC     | 0x0008 | Declared  static .                       |
|  ACC_FINAL      | 0x0010 | Declared  final ; never directly assigned to after object construction (JLS §17.5). |
|  ACC_VOLATILE   | 0x0040 | Declared  volatile ; cannot be cached.   |
|  ACC_TRANSIENT  | 0x0080 | Declared  transient ; not written or read by a persistent object manager. |
|  ACC_SYNTHETIC  | 0x1000 | Declared synthetic; not present in the source code. |
|  ACC_ENUM       | 0x4000 | Declared as an element of an  enum .     |


*/
