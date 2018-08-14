package com.cncounter.util.bytecode;

import org.apache.commons.codec.binary.Hex;

/**
 * Created on 2018-08-13.
 */
public class HexUtils {
    public static String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2){
            hex = "0" + hex;
        }
        return hex;
    }
    public static String byteArrayToHex(byte[] bytes){
        String result = "";
        if(null == bytes){
            return result;
        }
        for(int i=0; i< bytes.length; i++){
            byte b = bytes[i];
            result += byteToHex(b);
        }
        return result.toUpperCase();
    }


    public static String encodeHexString(byte[] data) {
        return Hex.encodeHexString(data);
    }
}
