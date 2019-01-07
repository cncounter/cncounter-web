package com.cncounter.test.aes;

import org.apache.commons.io.IOUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;

/**
 * AES大文件加密解密
 */
public class AESEncryptLargeFile {
    public static void main(String[] args) throws Throwable {
        //
        testEncrypt();
        //
        testDecrypt();
    }
    public static void testEncrypt() throws Throwable {
        //
        String inputFileName = "D:/Thunder_dl_7.9.44.5056.zip";
        String outputFileName = "D:/Thunder_dl_7.9.44.5056.zip.aes";
        //
        final String secret = "com.cipher.TestEncrptLargeFile";

        //定义输入流
        InputStream inputStream = new FileInputStream(inputFileName);
        //定义输出流
        OutputStream outputStream = new FileOutputStream(outputFileName);
        //
        AESEncodeLarge(secret, inputStream, outputStream);
        //
        System.out.println("testEncrypt():加密完成");
    }
    public static void testDecrypt() throws Throwable {
        //
        String inputFileName = "D:/Thunder_dl_7.9.44.5056.zip.aes";
        String outputFileName = "D:/Thunder_dl_7.9.44.5056_decrypt.zip";
        //
        final String secret = "com.cipher.TestEncrptLargeFile";

        //定义输入流
        InputStream inputStream = new FileInputStream(inputFileName);
        //定义输出流
        OutputStream outputStream = new FileOutputStream(outputFileName);
        //
        AESDecodeLarge(secret, inputStream, outputStream);
        //
        System.out.println("testDecrypt():解密完成");
    }

    /**
     * 通过AES 以流的形式 对文件进行加密
     *
     * @param secret       密码
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @return
     */
    public static boolean AESEncodeLarge(String secret, InputStream inputStream, OutputStream outputStream) throws Exception {
        //
        final String AES = "AES";
        //指定为AES加密，不区分大小写
        KeyGenerator keygen = KeyGenerator.getInstance(AES);
        //根据传入的key，生成随机流
        keygen.init(128, new SecureRandom(secret.getBytes()));
        //产生原始对称key
        SecretKey originKey = keygen.generateKey();
        //获取原始对称key的字节数组
        byte[] keyBytes = originKey.getEncoded();
        //生成AES密钥
        SecretKey key = new SecretKeySpec(keyBytes, AES);
        //生成密码器
        Cipher cipher = Cipher.getInstance(AES);
        //初始化为加密器
        cipher.init(Cipher.ENCRYPT_MODE, key);

        //定义缓存区，用于存储字节流
        byte[] buffer = new byte[1024];
        //定义加密输出流
        CipherOutputStream cipherOut = new CipherOutputStream(outputStream, cipher);

        // 拷贝, 则Cipher输出流会自动加密
        IOUtils.copyLarge(inputStream, cipherOut);
        //关闭IO流; TODO: 按道理-谁创建的stream谁负责关闭,此处不应该关闭
        IOUtils.closeQuietly(cipherOut);
        IOUtils.closeQuietly(inputStream);
        return true;
    }

    public static boolean AESDecodeLarge(String secret, InputStream inputStream, OutputStream outputStream) throws Exception {
        //
        final String AES = "AES";
        //指定为AES加密，不区分大小写
        KeyGenerator keygen = KeyGenerator.getInstance(AES);
        //根据传入的key，生成随机流
        keygen.init(128, new SecureRandom(secret.getBytes()));
        //产生原始对称key
        SecretKey originKey = keygen.generateKey();
        //获取原始对称key的字节数组
        byte[] keyBytes = originKey.getEncoded();
        //生成AES密钥
        SecretKey key = new SecretKeySpec(keyBytes, AES);
        //生成密码器
        Cipher cipher = Cipher.getInstance(AES);
        //初始化为-decode
        cipher.init(Cipher.DECRYPT_MODE, key);

        //定义缓存区，用于存储字节流
        byte[] buffer = new byte[1024];
        //定义加密输入流
        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);

        // 拷贝, 则Cipher输入流会自动解密
        IOUtils.copyLarge(cipherInputStream, outputStream);
        // 关闭IO流; TODO: 按道理-谁创建的stream谁负责关闭,此处不应该关闭
        IOUtils.closeQuietly(cipherInputStream);
        IOUtils.closeQuietly(outputStream);
        return true;
    }
}
