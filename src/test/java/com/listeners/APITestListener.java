package com.listeners;

import com.api.utils.AllureEnvWriterUtil;
import lombok.extern.log4j.Log4j2;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

@Log4j2
public class APITestListener implements ITestListener {
    public void onTestStart(ITestResult result) {
        log.info("========= Starting test: {} =========", result.getName());
        log.info("========= Test class: {} =========", result.getMethod().getTestClass());
        log.info("========= Description: {} =========", result.getMethod().getDescription());
        log.info("========= Groups: {} =========", Arrays.toString(result.getMethod().getGroups()));
    }

    public void onTestSuccess(ITestResult result) {
        long startTime = result.getStartMillis();
        long endTime = result.getEndMillis();
        log.info("========= Total Duration: {} ms =========", (endTime - startTime));
        log.info("{} - Test PASSED!!!", result.getName());


    }

    public void onTestFailure(ITestResult result) {
        log.error("{} - Test FAILED!!!", result.getName());
        log.error("Error Message: ");
        log.error(result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        log.warn("{} - Test SKIPPED!!!", result.getName());
        log.warn("Reason: ");
        log.warn(result.getThrowable());
    }

    public void onStart(ITestContext context) {
        log.info("********** Starting the Phoenix Framework **********");
        AllureEnvWriterUtil.createEnvProperties();
    }

    public void onFinish(ITestContext context) {
        log.info("********** FINISHED **********");
    }
}
