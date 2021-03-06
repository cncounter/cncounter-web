package com.cncounter.util.bytecode;

import com.cncounter.util.bytecode.constant.*;
import com.cncounter.util.bytecode.enums.AccessFlagsEnum;
import com.cncounter.util.bytecode.enums.ConstantTagEnum;

import java.io.UnsupportedEncodingException;
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
    public Integer accessFlagsNumber;// 访问标识位集合
    public Integer thisClassIndex;// 本类索引位置
    public Integer superClassIndex;// super类索引位置
    public Integer interfacesCount;// 实现的interface数量
    public Integer fieldsCountNumber;// fields 数量
    public Integer methodsCountNumber;// methods 数量
    public Integer attributesCountNumber;// methods 数量

    public List<ConstantItem> constantPoolList;// 常量池
    public List<Integer> interfacesIndexList;// 实现的interfaces列表
    public List<FieldInfo> fieldInfoList;//  fieldInfo 列表
    public List<MethodInfo> methodInfoList;//  methodInfo 列表
    public List<AttributeInfo> attributeInfoList; // attributeInfo 列表

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
        super.constantPoolCount = constantPoolCountBytes;
        String constantPoolCountHex = HexUtils.byteArrayToHex(this.constantPoolCount);
        //System.out.println("constantPoolCountHex=" + constantPoolCountHex);
        this.constantPoolCountNumber = Integer.parseInt(constantPoolCountHex, 16);
        index += constantPoolCountLength;
        // 解析常量池
        int constantPoolStartIndex = index; // 常量池起始索引位置
        //
        List<ConstantItem> constantItems = new ArrayList<ConstantItem>();
        for (int i = 1; i < this.constantPoolCountNumber; i++) {
            ConstantItem constantItem = parseConstantItem(rawContent, index);
            if (null == constantItem) {
                System.err.println("constantItem is null; index=" + index);
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
        // 解析 访问标识 accessFlags
        int accessFlagsLength = 2;
        byte[] accessFlagsBytes = new byte[accessFlagsLength];
        System.arraycopy(rawContent, index, accessFlagsBytes, 0, accessFlagsLength);
        super.accessFlags = accessFlagsBytes;
        String accessFlagsHex = HexUtils.byteArrayToHex(super.accessFlags);
        this.accessFlagsNumber = Integer.parseInt(accessFlagsHex, 16);
        index += accessFlagsLength;
        // 解析 thisClass
        int thisClassLength = 2;
        byte[] thisClassBytes = new byte[thisClassLength];
        System.arraycopy(rawContent, index, thisClassBytes, 0, thisClassLength);
        super.thisClass = thisClassBytes;
        String thisClassHex = HexUtils.byteArrayToHex(super.thisClass);
        this.thisClassIndex = Integer.parseInt(thisClassHex, 16);
        index += thisClassLength;
        // 解析 superClass
        int superClassLength = 2;
        byte[] superClassBytes = new byte[superClassLength];
        System.arraycopy(rawContent, index, superClassBytes, 0, superClassLength);
        super.superClass = superClassBytes;
        String superClassHex = HexUtils.byteArrayToHex(super.superClass);
        this.superClassIndex = Integer.parseInt(superClassHex, 16);
        index += superClassLength;

        //
        // 解析 interfacesCount
        int interfacesCountLength = 2;
        byte[] interfacesCountBytes = new byte[interfacesCountLength];
        System.arraycopy(rawContent, index, interfacesCountBytes, 0, interfacesCountLength);
        super.interfacesCount = interfacesCountBytes;
        String interfacesCountHex = HexUtils.byteArrayToHex(super.interfacesCount);
        this.interfacesCount = Integer.parseInt(interfacesCountHex, 16);
        index += interfacesCountLength;
        // 解析 interfaces
        this.interfacesIndexList = new ArrayList<Integer>();
        int interfacesStartIndex = index; // interfaces起始索引位置
        for (int i = 0; i < this.interfacesCount; i++) {
            // integer-2
            int interfacesItemLength = 2;
            byte[] interfacesItemBytes = new byte[interfacesItemLength];
            System.arraycopy(rawContent, index, interfacesItemBytes, 0, interfacesItemLength);
            String interfacesItemHex = HexUtils.byteArrayToHex(interfacesItemBytes);
            Integer item = Integer.parseInt(interfacesItemHex, 16);
            interfacesIndexList.add(item);
            index += interfacesItemLength;
        }
        int interfacesEndIndex = index; // interfaces起始索引位置
        // interfaces 索引长度
        int interfacesLength = interfacesEndIndex - interfacesStartIndex;
        byte[] interfacesBytes = new byte[interfacesLength];
        System.arraycopy(rawContent, interfacesStartIndex, interfacesBytes, 0, interfacesLength);
        super.interfaces = interfacesBytes;

        //
        // 解析 fieldsCount
        int fieldsCountLength = 2;
        byte[] fieldsCountBytes = new byte[fieldsCountLength];
        System.arraycopy(rawContent, index, fieldsCountBytes, 0, fieldsCountLength);
        super.fieldsCount = fieldsCountBytes;
        String fieldsCountHex = HexUtils.byteArrayToHex(super.fieldsCount);
        this.fieldsCountNumber = Integer.parseInt(fieldsCountHex, 16);
        index += fieldsCountLength;

        //
        int fieldsStartIndex = index;
        List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();
        // 解析 fields
        for (int i = 0; i < this.fieldsCountNumber; i++) {
            //
            FieldInfo fieldInfo = parseFieldInfo(rawContent, index);
            if (null == fieldInfo) {
                throw new RuntimeException("fieldInfo 不能为null; index=" + index);
            }
            //
            fieldInfoList.add(fieldInfo);
            //
            index += fieldInfo.getSize();
        }
        if (!fieldInfoList.isEmpty()) {
            this.fieldInfoList = fieldInfoList;
        }
        //
        int fieldsEndIndex = index;
        // fields 索引长度
        int fieldsLength = fieldsEndIndex - fieldsStartIndex;
        byte[] fieldsBytes = new byte[fieldsLength];
        System.arraycopy(rawContent, fieldsStartIndex, fieldsBytes, 0, fieldsLength);
        super.fields = fieldsBytes;

        //
        // 解析 methodsCount
        int methodsCountLength = 2;
        byte[] methodsCountBytes = new byte[methodsCountLength];
        System.arraycopy(rawContent, index, methodsCountBytes, 0, methodsCountLength);
        super.methodsCount = methodsCountBytes;
        String methodsCountHex = HexUtils.byteArrayToHex(super.methodsCount);
        this.methodsCountNumber = Integer.parseInt(methodsCountHex, 16);
        index += methodsCountLength;
        //

        //
        int methodsStartIndex = index;
        List<MethodInfo> methodInfoList = new ArrayList<MethodInfo>();
        // 解析 methods
        for (int i = 0; i < this.methodsCountNumber; i++) {
            //
            MethodInfo methodInfo = parseMethodInfo(rawContent, index);
            if (null == methodInfo) {
                throw new RuntimeException("methodInfo 不能为null; index=" + index);
            }
            //
            methodInfoList.add(methodInfo);
            //
            index += methodInfo.getSize();
        }
        if (!methodInfoList.isEmpty()) {
            this.methodInfoList = methodInfoList;
        }
        //
        int methodsEndIndex = index;
        // methods 索引长度
        int methodsLength = methodsEndIndex - methodsStartIndex;
        byte[] methodsBytes = new byte[methodsLength];
        System.arraycopy(rawContent, methodsStartIndex, methodsBytes, 0, methodsLength);
        super.methods = methodsBytes;

        //
        // 解析 attributesCount
        int attributesCountLength = 2;
        byte[] attributesCountBytes = new byte[attributesCountLength];
        System.arraycopy(rawContent, index, attributesCountBytes, 0, attributesCountLength);
        super.attributesCount = attributesCountBytes;
        String attributesCountHex = HexUtils.byteArrayToHex(super.attributesCount);
        this.attributesCountNumber = Integer.parseInt(attributesCountHex, 16);
        index += attributesCountLength;
        //
        int attributesStartIndex = index;
        //
        List<AttributeInfo> attributeInfoList = new ArrayList<AttributeInfo>();
        //
        for (int i = 0; i < this.attributesCountNumber; i++) {
            //
            AttributeInfo attributeInfo = parseAttributeInfo(rawContent, index);
            if (null == attributeInfo) {
                throw new RuntimeException("attributeInfo不能为null; index=" + index);
            }
            //
            attributeInfoList.add(attributeInfo);
            //
            index += attributeInfo.getSize();
        }
        if (!attributeInfoList.isEmpty()) {
            this.attributeInfoList = attributeInfoList;
        }
        //
        int attributesEndIndex = index;
        // attributes 索引长度
        int attributesLength = attributesEndIndex - attributesStartIndex;
        byte[] attributesBytes = new byte[attributesLength];
        System.arraycopy(rawContent, attributesStartIndex, attributesBytes, 0, attributesLength);
        super.attributes = attributesBytes;
        // 比对 index
        if(rawContent.length != index){
            System.err.println("====================Error");
            System.err.println("rawContent.length=" + rawContent.length);
            System.err.println("index=" + index);
            System.err.println("====================");
        }


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
                constantItem = parseConstantItemClass(rawContent, index);
                break;
            case CONSTANT_String:
                constantItem = parseConstantItemString(rawContent, index);
                break;
            // 3个的解析方式类似
            case CONSTANT_Fieldref:
            case CONSTANT_Methodref:
            case CONSTANT_InterfaceMethodref:
                constantItem = parseConstantItemRefBase(rawContent, index, tagEnum);
                break;
            case CONSTANT_NameAndType:
                constantItem = parseConstantItemNameAndType(rawContent, index);
                break;
            case CONSTANT_MethodHandle:
                constantItem = parseConstantItemMethodHandle(rawContent, index);
                break;
            case CONSTANT_MethodType:
                constantItem = parseConstantItemMethodType(rawContent, index);
                break;
            case CONSTANT_InvokeDynamic:
                constantItem = parseConstantItemInvokeDynamic(rawContent, index);
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
        //System.out.println("tagNumber=" + tagNumber + "; tagEnum.name=" + tagEnum.name());
        //System.out.println("----------------------------");

        //
        return constantItem;
    }

    private ConstantItem parseConstantItemUTF8(byte[] rawContent, int index) {
        //
        ConstantItemUTF8 item = new ConstantItemUTF8();
        // 解析 length
        int lengthLength = 2;
        byte[] lengthBytes = new byte[lengthLength];
        System.arraycopy(rawContent, index, lengthBytes, 0, lengthLength);
        item.length = lengthBytes;
        String lengthHex = HexUtils.byteArrayToHex(item.length);
        item.lengthNumber = Integer.parseInt(lengthHex, 16);
        index += lengthLength;
        // 解析 bytes
        int bytesLength = item.lengthNumber;
        byte[] bytesBytes = new byte[bytesLength];
        System.arraycopy(rawContent, index, bytesBytes, 0, bytesLength);
        item.bytes = bytesBytes;
        try {
            item.value = new String(item.bytes, UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        index += bytesLength;
        //
        item.info = ConstantItem.concatNew(item.length, item.bytes);
        //
        return item;
    }

    private ConstantItemInteger parseConstantItemInteger(byte[] rawContent, int index) {
        //
        ConstantItemInteger item = new ConstantItemInteger();
        // 解析 bytes
        int bytesLength = 4;
        byte[] bytesBytes = new byte[bytesLength];
        System.arraycopy(rawContent, index, bytesBytes, 0, bytesLength);
        item.bytes = bytesBytes;
        String bytesHex = HexUtils.byteArrayToHex(item.bytes);
        item.value = Integer.parseInt(bytesHex, 16);
        index += bytesLength;
        //
        item.info = item.bytes;
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
        item.value = ByteBuffer.wrap(item.bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        index += bytesLength;
        //
        item.info = item.bytes;
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
        item.value = ConstantItemLong.bytesToLong(targetBytes);
        //
        item.info = targetBytes;
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
        item.value = ConstantItemDouble.bytesToDouble(targetBytes);
        //
        item.info = targetBytes;
        //
        return item;
    }


    private ConstantItemClass parseConstantItemClass(byte[] rawContent, int index) {
        //
        ConstantItemClass item = new ConstantItemClass();
        // 解析 nameIndex
        int nameIndexLength = 2;
        byte[] nameIndexBytes = new byte[nameIndexLength];
        System.arraycopy(rawContent, index, nameIndexBytes, 0, nameIndexLength);
        item.nameIndex = nameIndexBytes;
        String nameIndexHex = HexUtils.byteArrayToHex(item.nameIndex);
        item.nameIndexNumber = Integer.parseInt(nameIndexHex, 16);
        index += nameIndexLength;
        //
        item.info = item.nameIndex;
        //
        return item;
    }

    private ConstantItemString parseConstantItemString(byte[] rawContent, int index) {
        //
        ConstantItemString item = new ConstantItemString();
        // 解析 stringIndex
        int stringIndexLength = 2;
        byte[] stringIndexBytes = new byte[stringIndexLength];
        System.arraycopy(rawContent, index, stringIndexBytes, 0, stringIndexLength);
        item.stringIndex = stringIndexBytes;
        String stringIndexHex = HexUtils.byteArrayToHex(item.stringIndex);
        item.stringIndexNumber = Integer.parseInt(stringIndexHex, 16);
        index += stringIndexLength;
        //
        item.info = item.stringIndex;
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

    private ConstantItemNameAndType parseConstantItemNameAndType(byte[] rawContent, int index) {
        //
        ConstantItemNameAndType item = new ConstantItemNameAndType();
        // 解析 nameIndex
        int nameIndexLength = 2;
        byte[] nameIndexBytes = new byte[nameIndexLength];
        System.arraycopy(rawContent, index, nameIndexBytes, 0, nameIndexLength);
        item.nameIndex = nameIndexBytes;
        String nameIndexHex = HexUtils.byteArrayToHex(item.nameIndex);
        item.nameIndexNumber = Integer.parseInt(nameIndexHex, 16);
        index += nameIndexLength;
        // 解析 descriptorIndex
        int descriptorIndexLength = 2;
        byte[] descriptorIndexBytes = new byte[descriptorIndexLength];
        System.arraycopy(rawContent, index, descriptorIndexBytes, 0, descriptorIndexLength);
        item.descriptorIndex = descriptorIndexBytes;
        String descriptorIndexHex = HexUtils.byteArrayToHex(item.descriptorIndex);
        item.descriptorIndexNumber = Integer.parseInt(descriptorIndexHex, 16);
        index += descriptorIndexLength;
        //
        item.info = ConstantItem.concatNew(item.nameIndex, item.descriptorIndex);
        //
        return item;
    }

    private ConstantItemMethodHandle parseConstantItemMethodHandle(byte[] rawContent, int index) {
        //
        ConstantItemMethodHandle item = new ConstantItemMethodHandle();
        // 解析 referenceKind
        int referenceKindLength = 1;
        byte[] referenceKindBytes = new byte[referenceKindLength];
        System.arraycopy(rawContent, index, referenceKindBytes, 0, referenceKindLength);
        item.referenceKind = referenceKindBytes;
        String referenceKindHex = HexUtils.byteArrayToHex(item.referenceKind);
        item.referenceKindNumber = Integer.parseInt(referenceKindHex, 16);
        index += referenceKindLength;
        // 解析 referenceIndex
        int referenceIndexLength = 1;
        byte[] referenceIndexBytes = new byte[referenceIndexLength];
        System.arraycopy(rawContent, index, referenceIndexBytes, 0, referenceIndexLength);
        item.referenceIndex = referenceIndexBytes;
        String referenceIndexHex = HexUtils.byteArrayToHex(item.referenceIndex);
        item.referenceIndexNumber = Integer.parseInt(referenceIndexHex, 16);
        index += referenceIndexLength;
        //
        item.info = ConstantItem.concatNew(item.referenceKind, item.referenceIndex);
        //
        return item;
    }

    private ConstantItemMethodType parseConstantItemMethodType(byte[] rawContent, int index) {
        //
        ConstantItemMethodType item = new ConstantItemMethodType();
        // 解析 descriptorIndex
        int descriptorIndexLength = 2;
        byte[] descriptorIndexBytes = new byte[descriptorIndexLength];
        System.arraycopy(rawContent, index, descriptorIndexBytes, 0, descriptorIndexLength);
        item.descriptorIndex = descriptorIndexBytes;
        String descriptorIndexHex = HexUtils.byteArrayToHex(item.descriptorIndex);
        item.descriptorIndexNumber = Integer.parseInt(descriptorIndexHex, 16);
        index += descriptorIndexLength;
        //
        item.info = item.descriptorIndex;
        //
        return item;
    }

    private ConstantItemInvokeDynamic parseConstantItemInvokeDynamic(byte[] rawContent, int index) {
        //
        ConstantItemInvokeDynamic item = new ConstantItemInvokeDynamic();
        // 解析 bootstrapMethodAttrIndex
        int bootstrapMethodAttrIndexLength = 2;
        byte[] bootstrapMethodAttrIndexBytes = new byte[bootstrapMethodAttrIndexLength];
        System.arraycopy(rawContent, index, bootstrapMethodAttrIndexBytes, 0, bootstrapMethodAttrIndexLength);
        item.bootstrapMethodAttrIndex = bootstrapMethodAttrIndexBytes;
        String bootstrapMethodAttrIndexHex = HexUtils.byteArrayToHex(item.bootstrapMethodAttrIndex);
        item.bootstrapMethodAttrIndexNumber = Integer.parseInt(bootstrapMethodAttrIndexHex, 16);
        index += bootstrapMethodAttrIndexLength;
        // 解析 nameAndTypeIndex
        int nameAndTypeIndexLength = 2;
        byte[] nameAndTypeIndexBytes = new byte[nameAndTypeIndexLength];
        System.arraycopy(rawContent, index, nameAndTypeIndexBytes, 0, nameAndTypeIndexLength);
        item.nameAndTypeIndex = nameAndTypeIndexBytes;
        String nameAndTypeIndexHex = HexUtils.byteArrayToHex(item.nameAndTypeIndex);
        item.nameAndTypeIndexNumber = Integer.parseInt(nameAndTypeIndexHex, 16);
        index += nameAndTypeIndexLength;
        //
        item.info = ConstantItem.concatNew(item.bootstrapMethodAttrIndex, item.nameAndTypeIndex);
        //
        return item;
    }


    public FieldInfo parseFieldInfo(byte[] rawContent, final int startIndex) {
        //
        FieldInfo fieldInfo = new FieldInfo();
        //
        int index = startIndex;

        // 解析 accessFlags
        int accessFlagsLength = 2;
        byte[] accessFlagsBytes = new byte[accessFlagsLength];
        System.arraycopy(rawContent, index, accessFlagsBytes, 0, accessFlagsLength);
        fieldInfo.accessFlags = accessFlagsBytes;
        String accessFlagsHex = HexUtils.byteArrayToHex(fieldInfo.accessFlags);
        fieldInfo.accessFlagsNumber = Integer.parseInt(accessFlagsHex, 16);
        index += accessFlagsLength;

        // 解析 nameIndex
        int nameIndexLength = 2;
        byte[] nameIndexBytes = new byte[nameIndexLength];
        System.arraycopy(rawContent, index, nameIndexBytes, 0, nameIndexLength);
        fieldInfo.nameIndex = nameIndexBytes;
        String nameIndexHex = HexUtils.byteArrayToHex(fieldInfo.nameIndex);
        fieldInfo.nameIndexNumber = Integer.parseInt(nameIndexHex, 16);
        index += nameIndexLength;

        // 解析 descriptorIndex
        int descriptorIndexLength = 2;
        byte[] descriptorIndexBytes = new byte[descriptorIndexLength];
        System.arraycopy(rawContent, index, descriptorIndexBytes, 0, descriptorIndexLength);
        fieldInfo.descriptorIndex = descriptorIndexBytes;
        String descriptorIndexHex = HexUtils.byteArrayToHex(fieldInfo.descriptorIndex);
        fieldInfo.descriptorIndexNumber = Integer.parseInt(descriptorIndexHex, 16);
        index += descriptorIndexLength;

        // 解析 attributesCount
        int attributesCountLength = 2;
        byte[] attributesCountBytes = new byte[attributesCountLength];
        System.arraycopy(rawContent, index, attributesCountBytes, 0, attributesCountLength);
        fieldInfo.attributesCount = attributesCountBytes;
        String attributesCountHex = HexUtils.byteArrayToHex(fieldInfo.attributesCount);
        fieldInfo.attributesCountNumber = Integer.parseInt(attributesCountHex, 16);
        index += attributesCountLength;
        //
        int attributesStartIndex = index;
        //
        List<AttributeInfo> attributeInfoList = new ArrayList<AttributeInfo>();
        //
        for (int i = 0; i < fieldInfo.attributesCountNumber; i++) {
            //
            AttributeInfo attributeInfo = parseAttributeInfo(rawContent, index);
            if (null == attributeInfo) {
                throw new RuntimeException("attributeInfo不能为null; index=" + index);
            }
            //
            attributeInfoList.add(attributeInfo);
            //
            index += attributeInfo.getSize();
        }
        if (!attributeInfoList.isEmpty()) {
            fieldInfo.attributeInfoList = attributeInfoList;
        }
        //
        int attributesEndIndex = index;
        // attributes 索引长度
        int attributesLength = attributesEndIndex - attributesStartIndex;
        byte[] attributesBytes = new byte[attributesLength];
        System.arraycopy(rawContent, attributesStartIndex, attributesBytes, 0, attributesLength);
        fieldInfo.attributes = attributesBytes;

        //
        return fieldInfo;
    }

    public MethodInfo parseMethodInfo(byte[] rawContent, final int startIndex) {
        //
        MethodInfo methodInfo = new MethodInfo();
        //
        int index = startIndex;

        // 解析 accessFlags
        int accessFlagsLength = 2;
        byte[] accessFlagsBytes = new byte[accessFlagsLength];
        System.arraycopy(rawContent, index, accessFlagsBytes, 0, accessFlagsLength);
        methodInfo.accessFlags = accessFlagsBytes;
        String accessFlagsHex = HexUtils.byteArrayToHex(methodInfo.accessFlags);
        methodInfo.accessFlagsNumber = Integer.parseInt(accessFlagsHex, 16);
        index += accessFlagsLength;

        // 解析 nameIndex
        int nameIndexLength = 2;
        byte[] nameIndexBytes = new byte[nameIndexLength];
        System.arraycopy(rawContent, index, nameIndexBytes, 0, nameIndexLength);
        methodInfo.nameIndex = nameIndexBytes;
        String nameIndexHex = HexUtils.byteArrayToHex(methodInfo.nameIndex);
        methodInfo.nameIndexNumber = Integer.parseInt(nameIndexHex, 16);
        index += nameIndexLength;

        // 解析 descriptorIndex
        int descriptorIndexLength = 2;
        byte[] descriptorIndexBytes = new byte[descriptorIndexLength];
        System.arraycopy(rawContent, index, descriptorIndexBytes, 0, descriptorIndexLength);
        methodInfo.descriptorIndex = descriptorIndexBytes;
        String descriptorIndexHex = HexUtils.byteArrayToHex(methodInfo.descriptorIndex);
        methodInfo.descriptorIndexNumber = Integer.parseInt(descriptorIndexHex, 16);
        index += descriptorIndexLength;

        // 解析 attributesCount
        int attributesCountLength = 2;
        byte[] attributesCountBytes = new byte[attributesCountLength];
        System.arraycopy(rawContent, index, attributesCountBytes, 0, attributesCountLength);
        methodInfo.attributesCount = attributesCountBytes;
        String attributesCountHex = HexUtils.byteArrayToHex(methodInfo.attributesCount);
        methodInfo.attributesCountNumber = Integer.parseInt(attributesCountHex, 16);
        index += attributesCountLength;
        //
        int attributesStartIndex = index;
        //
        List<AttributeInfo> attributeInfoList = new ArrayList<AttributeInfo>();
        //
        for (int i = 0; i < methodInfo.attributesCountNumber; i++) {
            //
            AttributeInfo attributeInfo = parseAttributeInfo(rawContent, index);
            if (null == attributeInfo) {
                throw new RuntimeException("attributeInfo不能为null; index=" + index);
            }
            //
            attributeInfoList.add(attributeInfo);
            //
            index += attributeInfo.getSize();
        }
        if (!attributeInfoList.isEmpty()) {
            methodInfo.attributeInfoList = attributeInfoList;
        }
        //
        int attributesEndIndex = index;
        // attributes 索引长度
        int attributesLength = attributesEndIndex - attributesStartIndex;
        byte[] attributesBytes = new byte[attributesLength];
        System.arraycopy(rawContent, attributesStartIndex, attributesBytes, 0, attributesLength);
        methodInfo.attributes = attributesBytes;

        //
        return methodInfo;
    }

    private AttributeInfo parseAttributeInfo(byte[] rawContent, int index) {
        //
        AttributeInfo attributeInfo = new AttributeInfo();
        // 解析 attributeNameIndex
        int attributeNameIndexLength = 2;
        byte[] attributeNameIndexBytes = new byte[attributeNameIndexLength];
        System.arraycopy(rawContent, index, attributeNameIndexBytes, 0, attributeNameIndexLength);
        attributeInfo.attributeNameIndex = attributeNameIndexBytes;
        String attributeNameIndexHex = HexUtils.byteArrayToHex(attributeInfo.attributeNameIndex);
        attributeInfo.attributeNameIndexNumber = Integer.parseInt(attributeNameIndexHex, 16);
        index += attributeNameIndexLength;

        // 解析 attributeLength
        int attributeLengthLength = 4;
        byte[] attributeLengthBytes = new byte[attributeLengthLength];
        System.arraycopy(rawContent, index, attributeLengthBytes, 0, attributeLengthLength);
        attributeInfo.attributeLength = attributeLengthBytes;
        String attributeLengthHex = HexUtils.byteArrayToHex(attributeInfo.attributeLength);
        attributeInfo.attributeLengthNumber = Integer.parseInt(attributeLengthHex, 16);
        index += attributeLengthLength;

        // 解析 info
        int infoLength = attributeInfo.attributeLengthNumber;
        byte[] infoBytes = new byte[infoLength];
        System.arraycopy(rawContent, index, infoBytes, 0, infoLength);
        attributeInfo.info = infoBytes;
        index += infoLength;

        //
        return attributeInfo;
    }


    @Override
    public String toString() {
        return toJSONString();
    }

    public String toJSONString() {
        //
        final String indent1 = "\n\t";
        //
        return "{" +
                "\n\tmagicNumber: \"" + magicNumber + '\"' +
                indent1 + ",minorVersionNumber: " + minorVersionNumber +
                indent1 + ",majorVersionNumber: " + majorVersionNumber +
                indent1 + ",constantPoolCountNumber: " + constantPoolCountNumber +
                // 垫一个0位置
                indent1 + ",constantPoolList: " + "[{}," +
                _constantPoolListToString("\n\t\t") +
                indent1 + "]" +

                indent1 + ",accessFlags: " + "\"" + AccessFlagsEnum.parseAccessFlags(accessFlagsNumber) + "\"" +
                indent1 + ",thisClassIndex: " + thisClassIndex +
                indent1 + ",superClassIndex: " + superClassIndex +
                indent1 + ",interfacesCount: " + interfacesCount +
                indent1 + ",interfacesIndexList: " + "[" +
                _interfacesIndexListToString("\n\t\t") + "]" +
                indent1 + ",fieldsCountNumber: " + fieldsCountNumber +
                indent1 + ",methodsCountNumber: " + methodsCountNumber +
                "\n}";
    }

    private String _constantPoolListToString(String indent) {
        if (null == constantPoolList || constantPoolList.isEmpty()) {
            return "";
        }
        String result = "";
        for (ConstantItem item : constantPoolList) {
            if (null == item) {
                continue;
            } else if (!result.isEmpty()) {
                result += ",";
            }
            result += item.toJSONString(indent);
        }

        //
        return result;
    }

    private String _interfacesIndexListToString(String indent) {
        if (null == interfacesIndexList || interfacesIndexList.isEmpty()) {
            return "";
        }
        String result = "";
        for (Integer item : interfacesIndexList) {
            if (null == item) {
                continue;
            } else if (!result.isEmpty()) {
                result += ",";
            }
            result += item;
        }

        //
        return result;
    }
}
