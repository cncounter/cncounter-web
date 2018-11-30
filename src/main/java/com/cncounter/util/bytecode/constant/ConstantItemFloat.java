package com.cncounter.util.bytecode.constant;

import com.cncounter.util.bytecode.enums.ConstantTagEnum;

/**
 * Created on 2018-08-15.
 */
public class ConstantItemFloat extends ConstantItem{
    public byte[] bytes; //                  u4;

    public Float value;

    public ConstantItemFloat() {
        super.tagEnum = ConstantTagEnum.CONSTANT_Float;
    }
}


/*
CONSTANT_Float_info {
    u1 tag;
    u4 bytes;
}
*/