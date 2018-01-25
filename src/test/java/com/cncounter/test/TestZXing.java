package com.cncounter.test;

import com.cncounter.util.zxing.ZXingUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestZXing {

	// 参考 http://www.cnblogs.com/jtmjx/archive/2012/06/18/2545209.html
	// 需要 JDK1.7 执行环境
	//@Test
	public void testZxing() {
		String content = "http://www.cnblogs.com/jtmjx/archive/2012/06/18/2545209.html";
		//
		OutputStream ostream = null;
		//
		//String pathname = "F:/"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".jpeg";
		String pathname = ""+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".jpeg";
		File file = new File(pathname);
		//
		try {
			ostream = new FileOutputStream(file);
			// 生成
			ZXingUtil.generateQrCode(content, ostream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(null != ostream){
				try {
					//
					ostream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//
		file = new File(pathname);
		if(file.exists() && file.isFile()){
			System.out.println("文件 "+ file.getAbsolutePath()+" 生成成功!");
		}
	}

    public void testZxingParse() {
        //
        String pathname = "E:\\qr0.jpg";
        File file = new File(pathname);
        //
        // 生成
        String content = ZXingUtil.parseQrCode(file);

        //
        System.out.println("content= "+ content);

    }
	
	public static void main(String[] args) {
		//new TestZXing().testZxing();
		new TestZXing().testZxingParse();
	}

}
