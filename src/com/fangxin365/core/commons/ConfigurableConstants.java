package com.fangxin365.core.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigurableConstants {
	
	protected static Log logger = LogFactory.getLog(ConfigurableConstants.class);

	protected static Properties p = new Properties();

	/**
     * 静态读入属性文件到Properties p变量中
     */
	protected static void init(String propertyFileName) {
		InputStream in = null;
		try {
			in = ConfigurableConstants.class.getClassLoader().getResourceAsStream(propertyFileName);
			if (in != null)
				p.load(in);
		} catch (IOException e) {
			logger.error("load " + propertyFileName + " into Constants error!");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close " + propertyFileName + " error!");
				}
			}
		}
	}

	/**
     * 封装了Properties类的getProperty函数,使p变量对子类透明.
     *
     * @param key property key.
     * @param defaultValue 当使用property key在properties中取不到值时的默认值.
     */
	public static String getProperty(String key, String defaultValue) {
		logger.debug("getProperty(" + key + "),return " + p.getProperty(key, defaultValue));
		return p.getProperty(key, defaultValue);
	}
}
