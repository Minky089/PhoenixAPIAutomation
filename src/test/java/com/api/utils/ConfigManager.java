package com.api.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();
    private static final String path = "config" + File.separator + "config.properties";
    private ConfigManager() {

    }
    static {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

        if (input == null) {
           throw  new RuntimeException("Can't find the config file at path: " + path);
        }

        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Read properties files from src/test/resources
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
