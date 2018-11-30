package com.cncounter.util.bytecode;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;

/**
 * 使用Commons BCEL
 *
 * @see {@linktourl https://commons.apache.org/proper/commons-bcel/ }
 * @see {@linktourl https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html }
 * @see {@linktourl http://www.softpedia.com/get/Programming/File-Editors/Java-Class-Viewer.shtml }
 *
 *
 */
public class TestJavaClassFileParser {

    public static void main(String[] args) {
        //
        String inputFileName = "E:\\CODE_ALL\\02_GIT_ALL\\cncounter-web\\target\\classes\\com\\cncounter\\util\\bytecode\\ByteCodeModel.class";
        try {
            //
            FileInputStream inputStream = new FileInputStream(inputFileName);
            //
            byte[] fileContent = IOUtils.toByteArray(inputStream);
            //
            ClassFileWrapper classFileWrapper = new ClassFileWrapper(fileContent);
            //
            System.out.println("================");
            System.out.println(classFileWrapper);


        } catch (Exception e) {
            e.printStackTrace();
        }

        //
    }
}
