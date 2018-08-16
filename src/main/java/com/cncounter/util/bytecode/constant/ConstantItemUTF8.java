package com.cncounter.util.bytecode.constant;

import com.cncounter.util.bytecode.HexUtils;
import com.cncounter.util.bytecode.enums.ConstantTagEnum;

import java.util.Arrays;

/**
 * 名称+类型
 */
public class ConstantItemUTF8 extends ConstantItem {
    public byte[] length; //           u2;
    public byte[] bytes; //            u1;[length]

    public Integer lengthNumber;
    public String value;


    public ConstantItemUTF8() {
        super.tagEnum = ConstantTagEnum.CONSTANT_Utf8;
    }


    public String toString(String indent) {
        return indent + "{" +
                indent + "\ttag:" + Integer.parseInt(HexUtils.byteArrayToHex(tag), 16) + "," +
                indent + "\ttagEnum:" + "\"" + tagEnum.name() + "\"" + "," +
                indent + "\tlengthNumber:" + lengthNumber + "," +
                indent + "\tvalue:" + "\"" + value + "\"" + "," +
                indent + "\tinfo:" + "\"" + HexUtils.byteArrayToHex(info) + "\"" +
                indent + '}';
    }

    @Override
    public String toString() {
        return "ConstantItemUTF8{" +
                "length=" + Arrays.toString(length) +
                ", bytes=" + Arrays.toString(bytes) +
                ", lengthNumber=" + lengthNumber +
                '}';
    }
}


/*
CONSTANT_Utf8_info {
    u1 tag;
    u2 length;
    u1 bytes[length];
}
*/