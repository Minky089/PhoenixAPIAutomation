package com.api.utils;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@Log4j2
public class AllureEnvWriterUtil {
    public static void createEnvProperties() {
        String folderPath = "target/allure-results";

        File file = new File(folderPath);
        //noinspection ResultOfMethodCallIgnored
        file.mkdirs();
        Properties prop = new Properties();
        prop.setProperty("Project Name", "Phoenix API Automation");
        prop.setProperty("Env", ConfigManager.env);
        prop.setProperty("BASE_URI", ConfigManager.getProperty("BASE_URI"));
        prop.setProperty("OS", System.getProperty("os.name"));
        prop.setProperty("OS Version", System.getProperty("os.version"));
        prop.setProperty("Java Version", System.getProperty("java.version"));

        try (FileWriter fw = new FileWriter(folderPath + "/environment.properties")) {
            prop.store(fw, "My Properties File");
            log.warn("Create the enviroment.properties file at {}", folderPath);
        } catch (IOException e) {
            log.error("Unable to create enviroment.properties file", e);
        }
    }
}
