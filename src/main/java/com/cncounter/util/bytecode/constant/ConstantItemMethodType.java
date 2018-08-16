package com.cncounter.util.bytecode.constant;

import com.cncounter.util.bytecode.enums.ConstantTagEnum;

/**
 * 方法
 */
public class ConstantItemMethodType extends ConstantItem{
    public byte[] descriptorIndex; //            u2;

    public Integer descriptorIndexNumber;


    public ConstantItemMethodType() {
        super.tagEnum = ConstantTagEnum.CONSTANT_MethodType;
    }


}


/*
CONSTANT_MethodType_info {
    u1 tag;
    u2 descriptor_index;
}
*/