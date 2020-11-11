package com.cncounter.test.util;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URLDecoderTest {


    @Test
    public void test() {

        String targetfilename = ("netty%E5%8E%8B%E6%B5%8B%E6%A0%88%E6%BA%A2%E5%87%BA.png");
        try {
            targetfilename = URLDecoder.decode(targetfilename, "UTF-8");
            System.out.println(targetfilename);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
