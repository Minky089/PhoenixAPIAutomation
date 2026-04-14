package com.dataproviders;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.api.utils.csv.CSVReaderUtil;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProvidersUtils {
private DataProvidersUtils() {}

    @DataProvider(name = "LoginAPIDataProvider")
    public static Iterator<UserBean> loginAPIDataProvider(){
        return CSVReaderUtil.loadBeanCSV("testData/LoginCreds.csv", UserBean.class);
    }

    @DataProvider(name = "CreateJobAPIDataProvider")
    public static Iterator<CreateJobPayload> createJobAPIDataProvider(){
        Iterator<CreateJobBean> createJobBeanIterator = CSVReaderUtil.loadBeanCSV("testData/CreateJobData.csv", CreateJobBean.class);

        List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
        CreateJobBean tempBean;
        CreateJobPayload tempPayload;
        while(createJobBeanIterator.hasNext()){
            tempBean = createJobBeanIterator.next();
            tempPayload = CreateJobBeanMapper.mapper(tempBean);
            payloadList.add(tempPayload);
        }

        return payloadList.iterator();
    }

    @DataProvider(name = "CreateJobAPIFakerDataProvider")
    public static Iterator<CreateJobPayload> createJobAPIDataFakerProvider(){
        String fakerCount = System.getProperty("fakerCount", "5");
        return FakerDataGenerator.generateFakeCreateJobData(Integer.parseInt(fakerCount));
    }

    @DataProvider(name = "LoginAPIJSONDataProvider")
    public static Iterator<UserCredentials> loginAPIJSONDataProvider() {
        return JsonReaderUtil.loadJSON("testData/LoginCreds.json", UserCredentials[].class);
    }

    @DataProvider(name = "CreateJobAPIJSONDataProvider")
    public static Iterator<CreateJobPayload> CreateJobAPIJSONDataProvider() {
        return JsonReaderUtil.loadJSON("testData/CreateJobData.json", CreateJobPayload[].class);
    }

}