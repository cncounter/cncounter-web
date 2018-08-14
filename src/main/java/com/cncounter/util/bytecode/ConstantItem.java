package com.cncounter.util.bytecode;

/**
 * 常量池-条目
 */
public class ConstantItem {
    public byte[] tag; //             u1; 类型
    public byte[] info; //            u1; 信息
    //
    public ConstantTagEnum tagEnum; // 对应的tag-enum

}

/*

cp_info {
    u1 tag;
    u1 info[];
}

*/