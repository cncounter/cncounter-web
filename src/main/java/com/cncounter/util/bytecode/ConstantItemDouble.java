package com.cncounter.util.bytecode;

/**
 * Created on 2018-08-15.
 */
public class ConstantItemDouble extends ConstantItem{
    public byte[] highBytes; //                  u4;
    public byte[] lowBytes; //                    u4;

    public Double value;

    public ConstantItemDouble() {
        super.tagEnum = ConstantTagEnum.CONSTANT_Double;
    }
}


/*
CONSTANT_Long_info {
    u1 tag;
    u4 high_bytes;
    u4 low_bytes;
}

CONSTANT_Double_info {
    u1 tag;
    u4 high_bytes;
    u4 low_bytes;
}
*/