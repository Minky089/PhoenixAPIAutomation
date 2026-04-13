package com.api.utils.csv;

import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class CSVReaderUtil {
    private CSVReaderUtil() {}

    public static Iterator<UserBean> loadCSV(String pathOfCSVFile) {
        //Sample file: testData/LoginCreds.csv
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
        assert is != null;
        InputStreamReader isr = new InputStreamReader(is);
        CSVReader csvReader = new CSVReader(isr);

        CsvToBean<UserBean> csVToBean = new CsvToBeanBuilder(csvReader)
                .withType(UserBean.class)
                .withIgnoreEmptyLine(true)
                .build();

        List<UserBean> userList = csVToBean.parse();
        return userList.iterator();
    }

}