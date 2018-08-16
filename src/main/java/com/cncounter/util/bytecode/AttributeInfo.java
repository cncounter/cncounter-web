package com.cncounter.util.bytecode;

/**
 * 特性
 */
public class AttributeInfo {
    public byte[] attributeNameIndex; //  u2; 特性名称对应的常量索引位置
    public byte[] attributeLength; //     u4; 信息长度
    public byte[] info; //                  ; 特性信息

    //
    public Integer attributeNameIndexNumber;
    public Integer attributeLengthNumber;


    public int getSize() {
        int size = 0;
        if (null != attributeNameIndex) {
            size += attributeNameIndex.length;
        }
        if (null != attributeLength) {
            size += attributeLength.length;
        }
        if (null != info) {
            size += info.length;
        }
        return size;
    }
}

/*
attribute_info {
    u2 attribute_name_index;
    u4 attribute_length;
    u1 info[attribute_length];
}
*/