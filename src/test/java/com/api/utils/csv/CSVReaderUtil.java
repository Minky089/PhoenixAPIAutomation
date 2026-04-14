package com.api.utils.csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class CSVReaderUtil {
    private CSVReaderUtil() {}

    public static <T> Iterator<T> loadBeanCSV(String pathOfCSVFile, Class<T> beanClass) {
        //Sample file: testData/LoginCreds.csv
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
        assert is != null;
        InputStreamReader isr = new InputStreamReader(is);
        CSVReader csvReader = new CSVReader(isr);

        CsvToBean<T> csVToBean = new CsvToBeanBuilder(csvReader)
                .withType(beanClass)
                .withIgnoreEmptyLine(true)
                .build();

        List<T> beanList = csVToBean.parse();
        return beanList.iterator();
    }
}