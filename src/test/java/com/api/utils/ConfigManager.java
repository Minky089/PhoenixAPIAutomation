package com.api.utils;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
public class ConfigManager {
    private static final Properties properties = new Properties();
    //blank final var are allowed to assign value later
    private static final String path;
    private static final String env;

    private ConfigManager() {
    }

    static {
        log.info("Reading env value passed from terminal");
        if(System.getenv("env") == null) {log.warn("Env value is not set... using qa");}
        env = System.getProperty("env", "qa").toLowerCase().trim();
        log.info("Running test in the {} env", env);
        switch (env) {
            case "dev" -> path = "config" + File.separator + "config.dev.properties";
            case "uat" -> path = "config" + File.separator + "config.uat.properties";
            default -> path = "config" + File.separator + "config.qa.properties";
        }
    }

    static {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

        if (input == null) {
            log.error("Can not find the config file at path: {}", path);
            throw new RuntimeException("Can't find the config file at path: " + path);
        }

        try {
            properties.load(input);
        } catch (IOException e) {
            log.error("Something went wrong... please check the file: {}", path, e);
            throw new RuntimeException(e);
        }
    }

    //Read properties files from src/test/resources
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}