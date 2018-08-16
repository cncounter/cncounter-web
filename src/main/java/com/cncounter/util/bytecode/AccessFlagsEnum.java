package com.cncounter.util.bytecode;

/**
 * 访问标识
 */
public enum AccessFlagsEnum {
    ACC_PUBLIC(0x0001, "public"),
    ACC_FINAL(0x0010, "final"),
    // super. 指令调用需要特殊处理(invokespecial)
    ACC_SUPER(0x0020, "[super.]"),
    ACC_INTERFACE(0x0200, "interface"),
    ACC_ABSTRACT(0x0400, "abstract"),
    ACC_SYNTHETIC(0x1000, "synchronized"),
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
        boolean isClass = true;

        //
        if(AccessFlagsEnum.ACC_PUBLIC.in(accessFlagsNumber)){
            result += " " + AccessFlagsEnum.ACC_PUBLIC.type;
        }
        if(AccessFlagsEnum.ACC_SUPER.in(accessFlagsNumber)){
            //result += " " + AccessFlagsEnum.ACC_SUPER.type;
        }
        if(AccessFlagsEnum.ACC_FINAL.in(accessFlagsNumber)){
            result += " " + AccessFlagsEnum.ACC_FINAL.type;
        }
        if(AccessFlagsEnum.ACC_ABSTRACT.in(accessFlagsNumber)){
            result += " " + AccessFlagsEnum.ACC_ABSTRACT.type;
        }
        if(AccessFlagsEnum.ACC_SYNTHETIC.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_SYNTHETIC.type;
        }
        if(AccessFlagsEnum.ACC_ANNOTATION.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_ANNOTATION.type;
            isClass = false;
        }
        if(AccessFlagsEnum.ACC_ENUM.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_ENUM.type;
            isClass = false;
        }
        if(AccessFlagsEnum.ACC_INTERFACE.in(accessFlagsNumber)) {
            result += " " + AccessFlagsEnum.ACC_INTERFACE.type;
            isClass = false;
        }
        if(isClass) {
            result += " " + "class";
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

*/
