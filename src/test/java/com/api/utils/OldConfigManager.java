package com.api.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class OldConfigManager {
    private static Properties properties = new Properties();

    private OldConfigManager() {

    }
    static {
        //Operation of loading the properties file in the memory.
        //Static block will be executed once during class loading time.
        File configFile = new File(System.getProperty("user.dir")  + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "config" + File.separator + "config.properties");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(configFile);
            properties.load(fileReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Read properties files from src/test/resources
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
