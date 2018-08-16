package com.cncounter.util.bytecode;

import java.nio.ByteBuffer;

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
        if (null != tag) {
            size += tag.length;
        }
        if (null != info) {
            size += info.length;
        }
        return size;
    }

    public String toString(String indent) {
        return indent + "{" +
                indent + "\ttag:" + Integer.parseInt(HexUtils.byteArrayToHex(tag), 16) + "," +
                indent + "\ttagEnum:" + "\"" + tagEnum.name() + "\"" + "," +
                indent + "\tinfo:" + "\"" + HexUtils.byteArrayToHex(info) + "\"" +
                indent + '}';
    }

    public static byte[] concatNew(byte[] arr1, byte[] arr2) {
        if (null == arr1) {
            arr1 = new byte[0];
        }
        if (null == arr2) {
            arr2 = new byte[0];
        }
        int size = arr1.length + arr2.length;
        byte[] targetArray = new byte[size];
        // copy
        System.arraycopy(arr1, 0, targetArray, 0, arr1.length);
        System.arraycopy(arr2, 0, targetArray, arr1.length - 1, arr2.length);
        //
        return targetArray;
    }


    public static double bytesToDouble(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer.getDouble();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer.getLong();
    }

//    public static long bytesToLong(byte[] bytes) {
//        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
//        buffer.put(bytes);
//        buffer.flip();//need flip
//        return buffer.getLong();
//    }
}

/*

cp_info {
    u1 tag;
    u1 info[];
}

*/