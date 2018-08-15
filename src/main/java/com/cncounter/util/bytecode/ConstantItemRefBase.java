package com.cncounter.util.bytecode;

/**
 * Created on 2018-08-15.
 */
public class ConstantItemRefBase extends ConstantItem{
    public byte[] classIndex; //                  u2;
    public byte[] nameAndTypeIndex; //            u2;

    public Integer classIndexNumber;
    public Integer nameAndTypeIndexNumber;

    public void fillInfoArray(){
        byte[] infoArray = concatNew(classIndex, nameAndTypeIndex);
        super.info = infoArray;
    }
}


/*

CONSTANT_Fieldref_info {
    u1 tag;
    u2 class_index;
    u2 name_and_type_index;
}

CONSTANT_Methodref_info {
    u1 tag;
    u2 class_index;
    u2 name_and_type_index;
}

CONSTANT_InterfaceMethodref_info {
    u1 tag;
    u2 class_index;
    u2 name_and_type_index;
}

 */