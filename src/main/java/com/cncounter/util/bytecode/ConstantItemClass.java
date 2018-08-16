package com.cncounter.util.bytecode;

/**
 * Created on 2018-08-15.
 */
public class ConstantItemClass extends ConstantItem{
    public byte[] nameIndex; //                  u2;

    public Integer nameIndexNumber;

    public ConstantItemClass() {
        super.tagEnum = ConstantTagEnum.CONSTANT_Class;
    }
}


/*
CONSTANT_Class_info {
    u1 tag;
    u2 name_index;
}
*/