package com.cncounter.util.bytecode;

/**
 * Created on 2018-08-15.
 */
public class ConstantItemClass extends ConstantItem {
    public byte[] nameIndex; //                  u2;

    public Integer nameIndexNumber; // 类名对应的常量索引位置,从1开始数

    public ConstantItemClass() {
        super.tagEnum = ConstantTagEnum.CONSTANT_Class;
    }

    public String toString(String indent) {
        return indent + "{" +
                indent + "\ttag:" + Integer.parseInt(HexUtils.byteArrayToHex(tag), 16) + "," +
                indent + "\ttagEnum:" + "\"" + tagEnum.name() + "\"" + "," +
                indent + "\tnameIndexNumber:" + nameIndexNumber + "," +
                indent + "\tinfo:" + "\"" + HexUtils.byteArrayToHex(info) + "\"" +
                indent + '}';
    }
}


/*
CONSTANT_Class_info {
    u1 tag;
    u2 name_index;
}
*/