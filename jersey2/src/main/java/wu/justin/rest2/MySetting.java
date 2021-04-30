package wu.justin.rest2;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

public enum MySetting {
	
	DbIp("db.ip");
	
	private static Logger log = Logger.getLogger(MySetting.class);
	
	private final String key;
	
	private static final String PROPERTY_FILE_NAME = "jersey2.properties";
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
        	URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        	if(url == null) {
        		throw new IllegalArgumentException ("Unable to locate the file: " +  fileName);
        	}
        	log.info(url);
        	inputStream = url.openStream();

        } catch (Exception e) {
        	log.error("Attempt to load the file '"+fileName+"' using Thread.currentThread().getContextClassLoader() failed.");
        }
        if (inputStream==null) {
        	// try class as backup, usually it is incorrect because ClassUtil maybe is in a jar
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
