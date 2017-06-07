package wu.justin.rest2;

import java.io.InputStream;
import java.util.Properties;

public enum MySetting {
	
	DbIp("db.ip");
	
	private final String key;
	
	private static final String PROPERTY_FILE_NAME = "api23.properties";
	private static Properties config;
	static {
        config = new Properties();
		try (InputStream is = createInputStream(PROPERTY_FILE_NAME)){
			config.load(is);
		} catch (Exception e) {
			System.err.println("SYSTEM ERROR: cannot load the property file: "+PROPERTY_FILE_NAME);
		}
	}
	
	MySetting(String key) {
		this.key = key;
	}
	
    private static InputStream createInputStream(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + fileName);
        } catch (Exception e) {
        	System.err.println("Attempt to load the file '"+fileName+"' using Thread.currentThread().getContextClassLoader() failed.");
        }
        if (inputStream==null) {
        	inputStream = MySetting.class.getResourceAsStream("/" + fileName);
        }
        if (inputStream == null)
            throw new IllegalArgumentException("Can't locate the file: " + fileName);
        return inputStream;
    }

	public String getValue() {
		return config.getProperty(key);
	}
}
