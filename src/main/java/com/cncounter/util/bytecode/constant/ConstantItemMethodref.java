package com.cncounter.util.bytecode.constant;

import com.cncounter.util.bytecode.enums.ConstantTagEnum;

/**
 * 常量条目-方法引用
 */
public class ConstantItemMethodref extends ConstantItemRefBase {
    public ConstantItemMethodref(){
        super.tagEnum = ConstantTagEnum.CONSTANT_Methodref;
    }
}


/*

CONSTANT_Methodref_info {
    u1 tag;
    u2 class_index;
    u2 name_and_type_index;
}

 */