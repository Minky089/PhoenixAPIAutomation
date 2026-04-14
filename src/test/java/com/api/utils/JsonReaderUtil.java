package com.api.utils;

import com.api.request.model.UserCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JsonReaderUtil {
    private JsonReaderUtil() {}

    public static <T> Iterator<T> loadJSON (String fileName, Class<T[]> clazz) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        T[] classArray;
        List<T> list;
        try {
            classArray = objectMapper.readValue(is, clazz);
            list = Arrays.asList(classArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list.iterator();
    }
}
