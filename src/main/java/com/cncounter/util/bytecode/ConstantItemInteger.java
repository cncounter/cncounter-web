package com.cncounter.util.bytecode;

/**
 * Created on 2018-08-15.
 */
public class ConstantItemInteger extends ConstantItem{
    public byte[] bytes; //                  u4;

    public Integer value;

    public ConstantItemInteger() {
        super.tagEnum = ConstantTagEnum.CONSTANT_Integer;
    }
}


/*
CONSTANT_Integer_info {
    u1 tag;
    u4 bytes;
}
*/