package com.api.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvUtil {
    private EnvUtil() {};
    private static final Dotenv dotenv;
    static {
        dotenv = Dotenv.load();
    }

    public static String getValue(String varName){
        return dotenv.get(varName);
    }
}
