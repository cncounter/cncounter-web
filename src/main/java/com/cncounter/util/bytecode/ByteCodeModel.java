package com.cncounter.util.bytecode;


import java.util.ArrayList;
import java.util.List;

/**
 * 字节码模型 - 基础类
 */
public class ByteCodeModel {
    // 原始字节数组
    private byte[] rawByteCode = null;
    // 十六进制编码
    private String hexString = null;
    // 子列表
    private List<ByteCodeModel> subModelList = new ArrayList<ByteCodeModel>();

    public byte[] getRawByteCode() {
        return this.rawByteCode;
    }

    public void setRawByteCode(byte[] rawByteCode) {
        this.rawByteCode = rawByteCode;
    }

    public String getHexString() {
        return this.hexString;
    }

    public void setHexString(String hexString) {
        this.hexString = hexString;
    }

    public List<ByteCodeModel> getSubModelList() {
        return this.subModelList;
    }

    public void setSubModelList(List<ByteCodeModel> subModelList) {
        this.subModelList = subModelList;
    }

    // 返回 16进制格式的字符串
    public String toHexString() {
        if (null != hexString) {
            return hexString;
        }
        if (null != rawByteCode) {
            hexString = HexUtils.encodeHexString(rawByteCode);
            return hexString;
        }

        return hexString;
    }

    // 返回 文本 格式
    public String toText() {
        return toHexString();
    }

    // 返回 HTML 格式
    public String toHTML() {
        return toHexString();
    }
}
