package com.cncounter.util.bytecode;

/**
 * Created on 2018-08-15.
 */
public class ConstantItemString extends ConstantItem{
    public byte[] stringIndex; //                  u2;

    public Integer stringIndexNumber;

    public ConstantItemString() {
        super.tagEnum = ConstantTagEnum.CONSTANT_String;
    }
}


/*
CONSTANT_String_info {
    u1 tag;
    u2 string_index;
}
*/