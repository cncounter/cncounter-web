package com.cncounter.demo.compile;

public class TieredCompilation {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1_000_000; i++) {
            Formatter formatter;
            if (i < 500_000) {
                formatter = new JsonFormatter();
            } else {
                formatter = new XmlFormatter();
            }
            formatter.format(new Article("Tiered Compilation in JVM", "CNC"));
        }
    }

}

/*
指定JVM启动参数: -XX:+PrintCompilation
*/