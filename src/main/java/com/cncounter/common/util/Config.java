package com.cncounter.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 配置信息
 */
public class Config {
    public static Logger logger = LoggerFactory.getLogger(Config.class);
    public static Config sysconfig = new Config("config/sysconfig");

    // 配置文件名称
    private String baseName = null;
    private Properties prop = null;

    public Config(String baseName) {
        this.baseName = baseName;
    }

    /**
     * 获得配置的值
     * @param key
     * @return
     */
    public String get(String key) {
        checkInit();
        if (null == key) {
            return null;
        }
        if (null != prop) {
            String value = prop.getProperty(key.trim());
            return value;
        }
        return null;
    }

    public String get(String key, String defaultValue) {
        String value = get(key);
        if (null == value || value.trim().isEmpty()) {
            value = defaultValue;
        }
        return value;
    }

    //
    private void checkInit() {
        if (null != prop) {
            return;
        }
        synchronized (this) {
            if (null != prop) {
                return;
            }
            init();
        }
    }

    public synchronized void init() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(this.baseName);
        //
        if (null == resourceBundle) {
            logger.error("找不到配置文件: " + this.baseName);
            return;
        }
        //
        prop = new Properties();
        //
        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = resourceBundle.getString(key);
            if (null != key && null != value) {
                prop.put(key.trim(), value.trim());
            }
        }
        logger.info("init()-完成: baseName={}", baseName);
    }
}
