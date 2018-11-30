package com.cncounter.util.bytecode.constant;

import com.cncounter.util.bytecode.enums.ConstantTagEnum;

/**
 * 常量条目-接口方法
 */
public class ConstantItemInterfaceMethodref extends ConstantItemRefBase {
    public ConstantItemInterfaceMethodref(){
        super.tagEnum = ConstantTagEnum.CONSTANT_InterfaceMethodref;
    }
}


/*

CONSTANT_InterfaceMethodref_info {
    u1 tag;
    u2 class_index;
    u2 name_and_type_index;
}

 */