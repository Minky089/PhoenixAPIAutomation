package com.dataproviders;

import com.api.utils.csv.CSVReaderUtil;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.DataProvider;

import java.util.Iterator;

public class DataProvidersUtils {
private DataProvidersUtils() {}

    @DataProvider(name = "LoginAPIDataProvider")
    public static Iterator<UserBean> loginAPIDataProvider(){
        return CSVReaderUtil.loadCSV("testData/LoginCreds.csv");
    }
}