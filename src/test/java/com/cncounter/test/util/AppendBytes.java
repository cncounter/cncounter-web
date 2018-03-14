package com.cncounter.test.util;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 * 在文件末尾加上几个字节:
 * 背景: 现在各个存储平台都有重复文件识别机制。
 * 当然还有文件屏蔽机制。
 * 我们要想办法, 突破这种限制;
 * 办法1: zip文件加密
 * 办法2: 在末尾增加随机的8个字节，在不影响正常使用的情况下。
 * Created on 2018/2/10.
 */
public class AppendBytes {

    public static void main(String[] args){
        //
        String dirPath = "E:\\TestAppendByte";
        String suffix = ".txt";
        File directory = new File(dirPath);
        int n = testDirs(directory, suffix);
        System.out.println("共写入成功数量:" + n);
    }

    public static int testDirs(File root, final String suffix){
        int num = 0;
        if(false == root.exists()){
            return num;
        }
        //
        if(root.isFile()){
            boolean result = append8Bytes(root);
            if(result){
                num++;
            }
            return num;
        }
        // 先处理上层文件
        File[] subFiles = root.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(suffix)){
                    return true;
                }
                return false;
            }
        });
        for(File subFile : subFiles){
            boolean result = append8Bytes(subFile);
            if(result){
                num++;
            }
        }
        // 再处理下级目录
        File[] subDirs = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.isDirectory()){
                    return true;
                }
                return false;
            }
        });
        for(File subDir : subDirs){
            if(subDir.isDirectory()){
                num += testDirs(subDir, suffix);
            }
        }
        //
        return num;
    }

    public static boolean append8Bytes(File file){
        int byteNum = 8;
        return appendBytes(file, byteNum);
    }

    public static boolean appendBytes(File file, int byteNum){
        if(null == file || false == file.exists() || false == file.isFile()){
            return Boolean.FALSE;
        }
        //
        boolean append = Boolean.TRUE;
        FileChannel fileChannel = null;
        //
        try {
            //FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(file, append);
            fileChannel = fileOutputStream.getChannel();
            //
            ByteBuffer byteBuffer = ByteBuffer.allocate(byteNum);
            byteBuffer.clear();
            byte[] bytes = new byte[byteNum];
            new Random().nextBytes(bytes); // 随机填充
            byteBuffer.put(bytes);
            // 指针移到开头; 限制可写入字节数
            byteBuffer.flip();
            // 写入可能不能一次性全部完成
            int num = 0;
            while(byteBuffer.hasRemaining()){
                num += fileChannel.write(byteBuffer);
            }
            // 内容强制写入磁盘
            boolean metaAlso = false;
            fileChannel.force(metaAlso);
            //
            if(num == byteNum){
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            System.out.println("操作失败:"+file.getAbsolutePath() + ";error="+e.getMessage());
        } finally {
            IOUtils.closeQuietly(fileChannel);
        }

        return Boolean.TRUE;
    }
}
