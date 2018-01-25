package com.cncounter.util.zxing;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 二维码图片包装工具<br/>
 * ZXing 3.1.0.jar 默认编译级别为JDK7,所以,JDK6的就只有自己编译源码咯。
 */
public class ZXingUtil {

    private static Logger logger = LoggerFactory.getLogger(ZXingUtil.class);

    /**
     * 生成二维码, 默认width = 400;height = 400;
	 * 
	 * @param content
	 *            需要生成的字符串内容
	 * @param output
	 *            输出流, 本方法不负责关闭输出流
	 */
	public static boolean generateQrCode(String content, OutputStream output) {
		//
		int width = 400;
		int height = 400;
		//
		return generateQrCode(content, output, width, height);
	}

	/**
	 * 
	 * 生成二维码
	 * 
	 * @param content
	 *            需要生成的字符串内容
	 * @param output
	 *            输出流, 本方法不负责关闭输出流
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return
	 */
	public static boolean generateQrCode(String content, OutputStream output,
			int width, int height) {
		// 防御编程
		if (null == content || null == output) {
			return false;
		}
		if (width < 10) {
			width = 400;
		}
		if (height < 10) {
			height = 400;
		}
		// 调用另一个方法
		BufferedImage image = generateQrCode(content, width, height);

		try {
			ImageIO.write(image, "jpeg", output);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		//
		return true;
	}

	/**
	 * 生成二维码图片
	 * 
	 * @param content
	 *            二维码内容,字符串
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return
	 */
	public static BufferedImage generateQrCode(String content, int width,
			int height) {
		// 防御编程
		if (null == content) {
			content = "";
		}
		BufferedImage image = null;
        //
        ErrorCorrectionLevel level = null;
        if(content.length() > 120){
            level = ErrorCorrectionLevel.L;
        } else if(content.length() > 100){
            level = ErrorCorrectionLevel.M;
        } else if(content.length() > 80){
            level = ErrorCorrectionLevel.Q;
        } else {
            level = ErrorCorrectionLevel.H;
        }

		// 配置项...
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 字符编码
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 容错率
		hints.put(EncodeHintType.ERROR_CORRECTION, level);
		// 白边大小
		Integer quietZoneInt = 0;
		hints.put(EncodeHintType.MARGIN, quietZoneInt);
		BitMatrix matrix = null;
		try {
			//
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			matrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE,
					width, height, hints);
			image = MatrixToImageWriter.toBufferedImage(matrix);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		//
		return image;
	}

	/**
	 * 解析二维码图片
	 * @param file 图片文件
	 * @return 二维码内容Text
	 */
	public static String parseQrCode(File file) {
		//
		String code = "";
		// 防御编程
		if (null == file || !file.exists() || !file.isFile()) {
			return code;
		}

		try {
			// 解析文件
			BufferedImage image = ImageIO.read(file);
			// 
			code = parseQrCode(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		return code;
	}
	

	/**
	 * 解析二维码图片.
     * 参考: https://github.com/zxing/zxing/blob/master/zxingorg/src/main/java/com/google/zxing/web/DecodeServlet.java#L359
	 * @param image 缓冲图片
	 * @return 二维码内容Text
	 */
	public static String parseQrCode(BufferedImage image ) {
		//
		String code = "";
		// 防御编程
		if (null == image || image.getHeight() < 10 || image.getWidth() < 10) {
			return code;
		}
        //
        Map<DecodeHintType,Object>  HINTS = new EnumMap<DecodeHintType,Object>(DecodeHintType.class);
        HINTS.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        HINTS.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
        Map<DecodeHintType,Object> HINTS_PURE = new EnumMap<DecodeHintType,Object>(HINTS);
        HINTS_PURE.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);

        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
        Collection<Result> results = new ArrayList<Result>(1);

        try {
            Reader reader = new MultiFormatReader();
            ReaderException savedException = null;
            try {
                // Look for multiple barcodes
                MultipleBarcodeReader multiReader = new GenericMultipleBarcodeReader(reader);
                Result[] theResults = multiReader.decodeMultiple(bitmap, HINTS);
                if (theResults != null) {
                    results.addAll(Arrays.asList(theResults));
                }
            } catch (ReaderException re) {
                savedException = re;
            }

            if (results.isEmpty()) {
                try {
                    // Look for pure barcode
                    Result theResult = reader.decode(bitmap, HINTS_PURE);
                    if (theResult != null) {
                        results.add(theResult);
                    }
                } catch (ReaderException re) {
                    savedException = re;
                }
            }

            if (results.isEmpty()) {
                try {
                    // Look for normal barcode in photo
                    Result theResult = reader.decode(bitmap, HINTS);
                    if (theResult != null) {
                        results.add(theResult);
                    }
                } catch (ReaderException re) {
                    savedException = re;
                }
            }

            if (results.isEmpty()) {
                try {
                    // Try again with other binarizer
                    BinaryBitmap hybridBitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result theResult = reader.decode(hybridBitmap, HINTS);
                    if (theResult != null) {
                        results.add(theResult);
                    }
                } catch (ReaderException re) {
                    savedException = re;
                }
            }

            if (results.isEmpty()) {
                try {
                    throw savedException == null ? NotFoundException.getNotFoundInstance() : savedException;
                } catch (FormatException e) {
                    logger.info(e.toString());
                } catch (ChecksumException e) {
                    logger.info(e.toString());
                } catch (ReaderException e) { // Including NotFoundException
                    logger.info(e.toString());
                }
                return "";
            }
            // 结果
            for(Result theResult: results){
                if (theResult != null) {
                    code = theResult.getText();
                }
                if(null != code && false==code.isEmpty()){
                    return code;
                }
            }

        } catch (RuntimeException re) {
            // Call out unexpected errors in the logger clearly
            logger.warn("Unexpected exception from library", re);
        }
		//
		return code;
	}
	
	//
	public static void main(String[] args) {
		String filePath = "D:/2bdaad640e.jpeg";
		String code = parseQrCode(new File(filePath));
		//
		System.out.println(code);
	}
}
