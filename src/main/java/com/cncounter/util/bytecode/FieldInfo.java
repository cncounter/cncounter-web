package com.cncounter.util.bytecode;

import java.util.Arrays;
import java.util.List;

/**
 * 字段结构体
 */
public class FieldInfo {
    public byte[] accessFlags; //       u2; 访问标识
    public byte[] nameIndex; //         u2; 字段名对应的常量索引位置
    public byte[] descriptorIndex; //   u2; 类型信息对应的常量索引位置
    public byte[] attributesCount; //   u2; 属性数量
    public byte[] attributes; //          ; 属性表; 个数=attributesCount

    //
    public Integer accessFlagsNumber;// 访问标识位集合
    public Integer nameIndexNumber;
    public Integer descriptorIndexNumber;
    public Integer attributesCountNumber;

    //
    public List<AttributeInfo> attributeInfoList;

    public int getSize() {
        int size = 0;
        if (null != accessFlags) {
            size += accessFlags.length;
        }
        if (null != nameIndex) {
            size += nameIndex.length;
        }
        if (null != descriptorIndex) {
            size += descriptorIndex.length;
        }
        if (null != attributesCount) {
            size += attributesCount.length;
        }
        if (null != attributes) {
            size += attributes.length;
        }
        return size;
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "accessFlags=" + Arrays.toString(accessFlags) +
                ", nameIndex=" + Arrays.toString(nameIndex) +
                ", descriptorIndex=" + Arrays.toString(descriptorIndex) +
                ", attributesCount=" + Arrays.toString(attributesCount) +
                ", attributes=" + Arrays.toString(attributes) +
                '}';
    }
}

/*
field_info {
    u2             access_flags;
    u2             name_index;
    u2             descriptor_index;
    u2             attributes_count;
    attribute_info attributes[attributes_count];
}
*/