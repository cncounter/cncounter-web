package com.cncounter.util.bytecode.constant;

import com.cncounter.util.bytecode.enums.ConstantTagEnum;

/**
 * Created on 2018-08-15.
 */
public class ConstantItemLong extends ConstantItem{
    public byte[] highBytes; //                  u4;
    public byte[] lowBytes; //                    u4;

    public Long value;

    public ConstantItemLong() {
        super.tagEnum = ConstantTagEnum.CONSTANT_Long;
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