package com.cncounter.util.bytecode;

/**
 * 名称+类型
 */
public class ConstantItemNameAndType extends ConstantItem{
    public byte[] nameIndex; //                  u2;
    public byte[] descriptorIndex; //            u2;

    public Integer nameIndexNumber;
    public Integer descriptorIndexNumber;


    public ConstantItemNameAndType() {
        super.tagEnum = ConstantTagEnum.CONSTANT_NameAndType;
    }


}


/*
CONSTANT_NameAndType_info {
    u1 tag;
    u2 name_index;
    u2 descriptor_index;
}
*/