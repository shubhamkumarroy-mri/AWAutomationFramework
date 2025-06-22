package main.java.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import main.java.utils.LoggerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {

    private static Properties properties;
    private static final Logger logger = LoggerUtils.getLogger(ConfigReader.class);

    static {
        try {
            String path = "src/main/resources/config.properties";
            FileInputStream fis = new FileInputStream(path);
            properties = new Properties();
            properties.load(fis);
            fis.close();
            logger.info("Configuration loaded successfully from " + path);
        } catch (IOException e) {
            System.out.println("Failed to load config.properties: " + e.getMessage());
            throw new RuntimeException("Configuration loading failed.");
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Key not found in config.properties: " + key);
        }
        return value;
    }
}
