package com.cncounter.util.bytecode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * @see {@linktourl https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html}
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

    public List<ConstantItem> constantPoolList;// 常量个数

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
        String minorVersionHex = HexUtils.byteArrayToHex(this.minorVersion);
        this.minorVersionNumber = Integer.parseInt(minorVersionHex, 16);
        index += minorVersionLength;
        // 解析 主版本号
        int majorVersionLength = 2;
        byte[] majorVersionBytes = new byte[majorVersionLength];
        System.arraycopy(rawContent, index, majorVersionBytes, 0, majorVersionLength);
        this.majorVersion = majorVersionBytes;
        String majorVersionHex = HexUtils.byteArrayToHex(this.majorVersion);
        this.majorVersionNumber = Integer.parseInt(majorVersionHex, 16);
        index += majorVersionLength;
        // 解析 常量个数
        int constantPoolCountLength = 2;
        byte[] constantPoolCountBytes = new byte[constantPoolCountLength];
        System.arraycopy(rawContent, index, constantPoolCountBytes, 0, constantPoolCountLength);
        this.constantPoolCount = constantPoolCountBytes;
        String constantPoolCountHex = HexUtils.byteArrayToHex(this.constantPoolCount);
        System.out.println("constantPoolCountHex=" + constantPoolCountHex);
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
        this.constantPoolList = constantItems;
        //
        int constantPoolEndIndex = index; // 常量池结束索引位置
        // 常量池索引长度
        int constantPoolLength = constantPoolEndIndex - constantPoolStartIndex;
        byte[] constantPoolBytes = new byte[constantPoolLength];
        System.arraycopy(rawContent, constantPoolStartIndex, constantPoolBytes, 0, constantPoolLength);
        super.constantPool = constantPoolBytes;

        //

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
        ConstantItem constantItem = null;
        //
        final int tagLength = 1;
        //
        //byte tagByte = rawContent[startIndex];
        byte[] tag = new byte[tagLength];
        tag[0] = tagByte;
        //
        int index = startIndex + tagLength;
        //
        switch (tagEnum) {
            case CONSTANT_Utf8:
                constantItem = parseConstantItemUTF8(rawContent, index);
                break;
            case CONSTANT_Integer:
                constantItem = parseConstantItemInteger(rawContent, index);
                break;
            case CONSTANT_Float:
                constantItem = parseConstantItemFloat(rawContent, index);
                break;
            case CONSTANT_Long:
                constantItem = parseConstantItemLong(rawContent, index);
                break;
            case CONSTANT_Double:
                constantItem = parseConstantItemDouble(rawContent, index);
                break;
            case CONSTANT_Class:
                break;
            case CONSTANT_String:
                break;
            //
            case CONSTANT_Fieldref:
            case CONSTANT_Methodref:
            case CONSTANT_InterfaceMethodref:
                constantItem = parseConstantItemRefBase(rawContent, index, tagEnum);
                break;
            case CONSTANT_NameAndType:
                break;
            case CONSTANT_MethodHandle:
                break;
            case CONSTANT_MethodType:
                break;
            case CONSTANT_InvokeDynamic:
                break;
            default:
                return null; // 不匹配...
        }

        if (null != constantItem) {
            constantItem.tag = tag;
            // 校验: 两者必须一致
            if (tagEnum != constantItem.tagEnum) {
                throw new RuntimeException("tagEnum不一致");
            }
        }

        //
        System.out.println("tagNumber=" + tagNumber + "; tagEnum.name=" + tagEnum.name());
        System.out.println("----------------------------");

        //
        return constantItem;
    }


    private ConstantItem parseConstantItemUTF8(byte[] rawContent, int index) {
        return null;
    }

    private ConstantItemInteger parseConstantItemInteger(byte[] rawContent, int index) {
        //
        ConstantItemInteger item = new ConstantItemInteger();
        // 解析 bytes
        int bytesLength = 4;
        byte[] bytesBytes = new byte[bytesLength];
        System.arraycopy(rawContent, index, bytesBytes, 0, bytesLength);
        item.bytes = bytesBytes;
        item.info = item.bytes;
        String bytesHex = HexUtils.byteArrayToHex(item.bytes);
        item.value = Integer.parseInt(bytesHex, 16);
        index += bytesLength;
        //
        return item;
    }

    private ConstantItemFloat parseConstantItemFloat(byte[] rawContent, int index) {
        //
        ConstantItemFloat item = new ConstantItemFloat();
        // 解析 bytes
        int bytesLength = 4;
        byte[] bytesBytes = new byte[bytesLength];
        System.arraycopy(rawContent, index, bytesBytes, 0, bytesLength);
        item.bytes = bytesBytes;
        item.info = item.bytes;
        item.value = ByteBuffer.wrap(item.bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        ;
        index += bytesLength;
        //
        return item;
    }

    private ConstantItemLong parseConstantItemLong(byte[] rawContent, int index) {
        //
        ConstantItemLong item = new ConstantItemLong();
        // 解析 high
        int highLength = 4;
        byte[] highBytes = new byte[highLength];
        System.arraycopy(rawContent, index, highBytes, 0, highLength);
        item.highBytes = highBytes;
        index += highLength;
        // 解析 low
        int lowLength = 4;
        byte[] lowBytes = new byte[lowLength];
        System.arraycopy(rawContent, index, lowBytes, 0, lowLength);
        item.lowBytes = lowBytes;
        index += lowLength;

        //
        byte[] targetBytes = ConstantItem.concatNew(item.highBytes, item.lowBytes);
        item.info = targetBytes;
        //
        item.value = ConstantItemLong.bytesToLong(targetBytes);
        //
        return item;
    }

    private ConstantItemDouble parseConstantItemDouble(byte[] rawContent, int index) {
        //
        ConstantItemDouble item = new ConstantItemDouble();
        // 解析 high
        int highLength = 4;
        byte[] highBytes = new byte[highLength];
        System.arraycopy(rawContent, index, highBytes, 0, highLength);
        item.highBytes = highBytes;
        index += highLength;
        // 解析 low
        int lowLength = 4;
        byte[] lowBytes = new byte[lowLength];
        System.arraycopy(rawContent, index, lowBytes, 0, lowLength);
        item.lowBytes = lowBytes;
        index += lowLength;

        //
        byte[] targetBytes = ConstantItem.concatNew(item.highBytes, item.lowBytes);
        item.info = targetBytes;
        //
        item.value = ConstantItemDouble.bytesToDouble(targetBytes);
        //
        return item;
    }

    // 3者解析方式是一样的;
    private ConstantItemRefBase parseConstantItemRefBase(byte[] rawContent, int index, ConstantTagEnum tagEnum) {
        //
        ConstantItemRefBase itemref = null;
        //
        switch (tagEnum) {
            case CONSTANT_Methodref:
                itemref = new ConstantItemMethodref();
                break;
            case CONSTANT_Fieldref:
                itemref = new ConstantItemFieldref();
                break;
            case CONSTANT_InterfaceMethodref:
                itemref = new ConstantItemInterfaceMethodref();
                break;
            default:
                return null;
        }


        //
        // 解析 classIndex
        int classIndexLength = 2;
        byte[] classIndexBytes = new byte[classIndexLength];
        System.arraycopy(rawContent, index, classIndexBytes, 0, classIndexLength);
        itemref.classIndex = classIndexBytes;
        String classIndexHex = HexUtils.byteArrayToHex(itemref.classIndex);
        itemref.classIndexNumber = Integer.parseInt(classIndexHex, 16);
        index += classIndexLength;
        //
        // 解析 nameAndTypeIndex
        int nameAndTypeIndexLength = 2;
        byte[] nameAndTypeIndexBytes = new byte[nameAndTypeIndexLength];
        System.arraycopy(rawContent, index, nameAndTypeIndexBytes, 0, nameAndTypeIndexLength);
        itemref.nameAndTypeIndex = nameAndTypeIndexBytes;
        String nameAndTypeIndexHex = HexUtils.byteArrayToHex(itemref.nameAndTypeIndex);
        itemref.nameAndTypeIndexNumber = Integer.parseInt(nameAndTypeIndexHex, 16);
        index += nameAndTypeIndexLength;

        // 填充 info[]
        itemref.fillInfoArray();
        //
        return itemref;
    }

    @Override
    public String toString() {
        return "{" +
                "\n\tmagicNumber: \"" + magicNumber + '\"' +
                ",\n\tminorVersionNumber: " + minorVersionNumber +
                ",\n\tmajorVersionNumber: " + majorVersionNumber +
                ",\n\tconstantPoolCountNumber: " + constantPoolCountNumber +
                ",\n\tconstantPoolList: " + "[" +
                _constantPoolListToString("\n\t\t") +
                "\n\t]" +
                "\n}";
    }

    private String _constantPoolListToString(String indent) {
        if (null == constantPoolList || constantPoolList.isEmpty()) {
            return "";
        }
        String result = "";
        for (ConstantItem item : constantPoolList) {
            if (null == indent) {
                continue;
            } else {
                result += ",";
            }
            result += item.toString(indent);
        }

        //
        return result;
    }
}
