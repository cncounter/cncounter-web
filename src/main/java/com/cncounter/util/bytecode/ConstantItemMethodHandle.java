package com.cncounter.util.bytecode;

/**
 * 名称+类型
 */
public class ConstantItemMethodHandle extends ConstantItem{
    public byte[] referenceKind; //             u1;
    public byte[] referenceIndex; //            u1;

    public Integer referenceKindNumber;
    public Integer referenceIndexNumber;


    public ConstantItemMethodHandle() {
        super.tagEnum = ConstantTagEnum.CONSTANT_MethodHandle;
    }


}


/*
CONSTANT_MethodHandle_info {
    u1 tag;
    u1 reference_kind;
    u2 reference_index;
}
*/