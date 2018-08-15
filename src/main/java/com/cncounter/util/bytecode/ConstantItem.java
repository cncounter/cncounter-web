package com.cncounter.util.bytecode;

/**
 * 常量池-条目
 */
public class ConstantItem {
    public byte[] tag; //             u1; 类型
    public byte[] info; //            u1; 内容
    //
    public ConstantTagEnum tagEnum; // 对应的tag-enum

    public int getSize() {
        int size = 0;
        if(null != tag){
            size += tag.length;
        }
        if(null != info){
            size += info.length;
        }
        return size;
    }
}

/*

cp_info {
    u1 tag;
    u1 info[];
}

*/