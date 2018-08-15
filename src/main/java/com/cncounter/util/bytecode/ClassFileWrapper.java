package com.cncounter.util.bytecode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018-08-14.
 */
public class ClassFileWrapper extends ClassFile {
    //
    public static String UTF_8 = "UTF-8";
    public static int u2 = 2;
    public static int u4 = 4;

    private byte[] rawContent; // 原始内容

    public String magicNumber;// 魔数
    public Integer minorVersionNumber;// 次版本号
    public Integer majorVersionNumber;// 主版本号
    public Integer constantPoolCountNumber;// 常量个数

    public ClassFileWrapper(byte[] rawContent) {
        this.rawContent = rawContent;
        parseContent();
    }

    // 解析
    private void parseContent() {
        if (null == rawContent) {
            throw new NullPointerException();
        }
        //
        int index = 0;
        // 解析魔数
        int magicLength = 4;
        byte[] magicBytes = new byte[magicLength];
        System.arraycopy(rawContent, index, magicBytes, 0, magicLength);
        this.magic = magicBytes;
        this.magicNumber = HexUtils.byteArrayToHex(magic);
        index += magicLength;
        // 解析 次版本号
        int minorVersionLength = 2;
        byte[] minorVersionBytes = new byte[minorVersionLength];
        System.arraycopy(rawContent, index, minorVersionBytes, 0, minorVersionLength);
        this.minorVersion = minorVersionBytes;
        String minorVersionHex = HexUtils.byteArrayToHex(minorVersion);
        this.minorVersionNumber = Integer.parseInt(minorVersionHex, 16);
        index += minorVersionLength;
        // 解析 主版本号
        int majorVersionLength = 2;
        byte[] majorVersionBytes = new byte[majorVersionLength];
        System.arraycopy(rawContent, index, majorVersionBytes, 0, majorVersionLength);
        this.majorVersion = majorVersionBytes;
        String majorVersionHex = HexUtils.byteArrayToHex(majorVersion);
        this.majorVersionNumber = Integer.parseInt(majorVersionHex, 16);
        index += majorVersionLength;
        // 解析 常量个数
        int constantPoolCountLength = 2;
        byte[] constantPoolCountBytes = new byte[constantPoolCountLength];
        System.arraycopy(rawContent, index, constantPoolCountBytes, 0, constantPoolCountLength);
        this.constantPoolCount = constantPoolCountBytes;
        String constantPoolCountHex = HexUtils.byteArrayToHex(constantPoolCount);
        System.err.println("constantPoolCountHex="+constantPoolCountHex);
        this.constantPoolCountNumber = Integer.parseInt(constantPoolCountHex, 16);
        index += constantPoolCountLength;
        // 解析常量池
        int constantPoolStartIndex = index; // 常量池起始索引位置
        //
        List<ConstantItem> constantItems = new ArrayList<ConstantItem>(this.constantPoolCountNumber - 1);
        for (int i = 1; i <= this.constantPoolCountNumber; i++) {
            ConstantItem constantItem = parseConstantItem(rawContent, index);
            if (null == constantItem) {
                break;
            }
            constantItems.add(constantItem);
            index += constantItem.getSize();
        }
        //
        int constantPoolEndIndex = index; // 常量池结束索引位置
        int constantPoolLength = constantPoolEndIndex - constantPoolStartIndex; // 常量池索引长度
        byte[] constantPoolBytes = new byte[constantPoolLength];
        System.arraycopy(rawContent, constantPoolStartIndex, constantPoolBytes, 0, constantPoolLength);

    }


    public ConstantItem parseConstantItem(byte[] rawContent, int startIndex) {
        //
        //
        byte tagByte = rawContent[startIndex];
        int tagNumber = (int) tagByte;
        ConstantTagEnum tagEnum = ConstantTagEnum.parseByTag(tagNumber);
        //
        if (null == tagEnum) {
            return null;
        }
        //
        ConstantItem constantItem = parseConstantItemBy(rawContent, startIndex, tagEnum);
        int index = startIndex + constantItem.getSize();
        //
        System.out.println("tagNumber=" + tagNumber);
        System.out.println("tagEnum.name=" + tagEnum.name());
        System.out.println("----------------------------");

        //
        return constantItem;
    }

    private ConstantItem parseConstantItemBy(final byte[] rawContent, final int startIndex, final ConstantTagEnum tagEnum) {
        //
        ConstantItem constantItem = null;
        //
        final int tagLength = 1;
        //
        byte tagByte = rawContent[startIndex];
        byte[] tag = new byte[tagLength];
        tag[0] = tagByte;
        //
        int index = startIndex + tagLength;
        //
        switch (tagEnum) {
            case CONSTANT_Utf8:
                constantItem = parseConstantItemUTF8(rawContent, index);
                break;
            case CONSTANT_Methodref:
                constantItem = parseConstantItemMethodref(rawContent, index);
                break;
            default:
                break;
        }

        if (null != constantItem) {
            constantItem.tag = tag;
            // 校验: 两者必须一致
            if (tagEnum != constantItem.tagEnum) {
                throw new RuntimeException("tagEnum不一致");
            }
        }
        //
        return constantItem;
    }

    private ConstantItemMethodref parseConstantItemMethodref(byte[] rawContent, int index) {
        System.out.println("parseConstantItemMethodref()-invoked");
        //
        ConstantItemMethodref methodref = new ConstantItemMethodref();
        //

        //
        return methodref;
    }

    private ConstantItem parseConstantItemUTF8(byte[] rawContent, int index) {
        return null;
    }

    @Override
    public String toString() {
        return "ClassFileWrapper{" +
                "\n\tmagicNumber: \"" + magicNumber + '\"' +
                ",\n\tminorVersionNumber: " + minorVersionNumber +
                ",\n\tmajorVersionNumber: " + majorVersionNumber +
                ",\n\tconstantPoolCountNumber: " + constantPoolCountNumber +
                "\n}";
    }
}
