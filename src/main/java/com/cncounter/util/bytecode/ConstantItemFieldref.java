package com.cncounter.util.bytecode;

/**
 * 常量条目-字段
 */
public class ConstantItemFieldref extends ConstantItemRefBase {

    public ConstantItemFieldref(){
        super.tagEnum = ConstantTagEnum.CONSTANT_Fieldref;
    }
}


/*

CONSTANT_Fieldref_info {
    u1 tag;
    u2 class_index;
    u2 name_and_type_index;
}

 */