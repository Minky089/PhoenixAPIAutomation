package com.api.services;

import com.api.constant.Roles;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import static com.api.utils.SpecUtil.getRequestSpecWithAuth;
import static io.restassured.RestAssured.given;

@Log4j2
public class JobService {
    private static final String CREATE_JOB_ENDPOINT = "job/create";
    private static final String JOB_SEARCH_ENDPOINT = "job/search";

    private void  prepareRequestLog(String endpoint, Roles role){log.info("Preparing request for the : {} endpoint, with {} role", endpoint,  role);}

    @Step("Creating Inwarranty Job with Create Job API")
    public Response createJob(Roles role, Object createJobPayload) {
        prepareRequestLog(CREATE_JOB_ENDPOINT,  role);
        return given().spec(getRequestSpecWithAuth(role, createJobPayload))
                .when().post(CREATE_JOB_ENDPOINT);
    }

    @Step("Preparing Search API request")
    public Response jobSearch(Roles role, Object searchJobPayload) {
        prepareRequestLog(JOB_SEARCH_ENDPOINT,  role);
        return given().spec(getRequestSpecWithAuth(role, searchJobPayload))
                .when().post(JOB_SEARCH_ENDPOINT);
    }
}