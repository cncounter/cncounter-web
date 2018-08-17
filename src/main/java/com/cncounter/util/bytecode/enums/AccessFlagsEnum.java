package com.cncounter.util.bytecode.enums;

/**
 * 访问标识
 */
public enum AccessFlagsEnum {
    //
    ACC_PUBLIC(0x0001, "public", "class|field|method"),
    ACC_PRIVATE(0x0002, "private", "field|method"),
    ACC_PROTECTED(0x0004, "protected", "field|method"),
    ACC_STATIC(0x0008, "static", "field|method"),
    //
    ACC_FINAL(0x0010, "final", "class|field|method"),
    // super. 指令调用需要特殊处理(invokespecial)
    ACC_SUPER(0x0020, "[super.]", "class"),
    ACC_SYNCHRONIZED(0x0020, "synchronized", "method"),//; method
    ACC_VOLATILE(0x0040, "volatile", "field"), // ; field
    ACC_BRIDGE(0x0040, "bridge", "method"), // generate by compiler; method
    ACC_TRANSIENT(0x0080, "transient", "field"),
    ACC_VARARGS(0x0080, "...", "method"),// ; method; 可变参数
    //
    ACC_NATIVE(0x0100, "native", "method"),
    ACC_INTERFACE(0x0200, "interface", "class"),
    ACC_ABSTRACT(0x0400, "abstract", "class|method"),
    ACC_STRICT(0x0800, "strict", "method"),
    // 由编译器自动生成-标记
    ACC_SYNTHETIC(0x1000, "<synthetic>", "class|field|method"),
    ACC_ANNOTATION(0x2000, "@interface", "class"), //; Class
    ACC_ENUM(0x4000, "enum", "class|field");

    // 由谁使用
    public static final String USED_CLASS = "class";
    public static final String USED_FIELD = "field";
    public static final String USED_METHOD = "method";
    //
    public int value;
    public String type;
    public String usedBy;

    AccessFlagsEnum(int value, String type, String usedBy) {
        this.value = value;
        this.type = type;
        this.usedBy = usedBy;
    }

    // 解析标志位
    public static String parseAccessFlags(int accessFlagsNumber) {
        String result = "";
        //
        // 可见范围
        if (AccessFlagsEnum.ACC_PUBLIC.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_PUBLIC.type;
        }
        if (AccessFlagsEnum.ACC_PRIVATE.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_PRIVATE.type;
        }
        if (AccessFlagsEnum.ACC_PROTECTED.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_PROTECTED.type;
        }
        //
        if (AccessFlagsEnum.ACC_STATIC.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_STATIC.type;
        }
        if (AccessFlagsEnum.ACC_FINAL.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_FINAL.type;
        }
        if (AccessFlagsEnum.ACC_ABSTRACT.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_ABSTRACT.type;
        }
        if (AccessFlagsEnum.ACC_VOLATILE.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_VOLATILE.type;
        }
        if (AccessFlagsEnum.ACC_TRANSIENT.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_TRANSIENT.type;
        }
        //
        if (AccessFlagsEnum.ACC_NATIVE.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_NATIVE.type;
        }
        if (AccessFlagsEnum.ACC_ANNOTATION.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_ANNOTATION.type;
        }
        if (AccessFlagsEnum.ACC_ENUM.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_ENUM.type;
        }
        if (AccessFlagsEnum.ACC_INTERFACE.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_INTERFACE.type;
        }
        if (AccessFlagsEnum.ACC_STRICT.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_STRICT.type;
        }
        // 特殊标记
        //
        if (AccessFlagsEnum.ACC_SUPER.in(accessFlagsNumber)) {
            //result += " " + AccessFlagsEnum.ACC_SUPER.type;
        }
        if (AccessFlagsEnum.ACC_SYNTHETIC.in(accessFlagsNumber)) {
            //result += " " + AccessFlagsEnum.ACC_SYNTHETIC.type;
        }

        //
        return result;
    }

    /**
     * 拥有标志位
     *
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


# 方法访问修饰
Table 4.6-A. Method access and property flags

| Flag Name          | Value  | Interpretation                           |
| ------------------ | ------ | ---------------------------------------- |
|  ACC_PUBLIC        | 0x0001 | Declared  public ; may be accessed from outside its package. |
|  ACC_PRIVATE       | 0x0002 | Declared  private ; accessible only within the defining class. |
|  ACC_PROTECTED     | 0x0004 | Declared  protected ; may be accessed within subclasses. |
|  ACC_STATIC        | 0x0008 | Declared  static .                       |
|  ACC_FINAL         | 0x0010 | Declared  final ; must not be overridden ([§5.4.5](#jvms-5.4.5)). |
|  ACC_SYNCHRONIZED  | 0x0020 | Declared  synchronized ; invocation is wrapped by a monitor use. |
|  ACC_BRIDGE        | 0x0040 | A bridge method, generated by the compiler. |
|  ACC_VARARGS       | 0x0080 | Declared with variable number of arguments. |
|  ACC_NATIVE        | 0x0100 | Declared  native ; implemented in a language other than Java. |
|  ACC_ABSTRACT      | 0x0400 | Declared  abstract ; no implementation is provided. |
|  ACC_STRICT        | 0x0800 | Declared  strictfp ; floating-point mode is FP-strict. |
|  ACC_SYNTHETIC     | 0x1000 | Declared synthetic; not present in the source code. |

*/
