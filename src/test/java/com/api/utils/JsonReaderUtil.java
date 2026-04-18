package com.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Log4j2
public class JsonReaderUtil {
    private JsonReaderUtil() {}

    public static <T> Iterator<T> loadJSON (String fileName, Class<T[]> clazz) {
        log.info("Reading JSON from file: {}", fileName);
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        T[] classArray;
        List<T> list;
        try {
            log.info("Converting JSON data to bean class: {}", clazz);
            classArray = objectMapper.readValue(is, clazz);
            list = Arrays.asList(classArray);
        } catch (IOException e) {
            log.error("Can not read data from JSON file: {}", fileName, e);
            throw new RuntimeException(e);
        }

        return list.iterator();
    }
}
