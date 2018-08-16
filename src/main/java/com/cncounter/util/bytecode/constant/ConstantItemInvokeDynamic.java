package com.cncounter.util.bytecode.constant;

import com.cncounter.util.bytecode.enums.ConstantTagEnum;

/**
 *
 */
public class ConstantItemInvokeDynamic extends ConstantItem{
    public byte[] bootstrapMethodAttrIndex; //            u2;
    public byte[] nameAndTypeIndex;         //            u2;

    public Integer bootstrapMethodAttrIndexNumber;
    public Integer nameAndTypeIndexNumber;


    public ConstantItemInvokeDynamic() {
        super.tagEnum = ConstantTagEnum.CONSTANT_InvokeDynamic;
    }


}


/*
CONSTANT_InvokeDynamic_info {
    u1 tag;
    u2 bootstrap_method_attr_index;
    u2 name_and_type_index;
}
*/