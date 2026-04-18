package com.api.utils;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EnvUtil {
    private EnvUtil() {};
    private static final Dotenv dotenv;
    static {
        log.info("Loading the .env file...");
        dotenv = Dotenv.load();
    }

    public static String getValue(String varName){
        log.info("Reading the value of {} from the .env", varName);
        return dotenv.get(varName);
    }
}
