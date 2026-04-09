package com.api.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();
    //blank final var are allowed to assign value later
    private static final String path;
    private static final String env;

    private ConfigManager() {
    }

    static {
        env = System.getProperty("env", "qa").toLowerCase().trim(); //default env is qa if no param pass in
        switch (env) {
            case "dev": {
                path = "config" + File.separator + "config.dev.properties";
                break;
            }
            case "uat": {
                path = "config" + File.separator + "config.uat.properties";
                break;
            }
            default: {
                path = "config" + File.separator + "config.qa.properties";
                break;
            }
        }
    }

    static {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

        if (input == null) {
            throw new RuntimeException("Can't find the config file at path: " + path);
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
