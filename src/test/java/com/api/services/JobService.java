package com.api.services;

import com.api.constant.Roles;
import io.restassured.response.Response;

import static com.api.utils.SpecUtil.getRequestSpecWithAuth;
import static io.restassured.RestAssured.given;

public class JobService {
    private static final String CREATE_JOB_ENDPOINT = "job/create";

    public Response createJob(Roles role, Object createJobPayload) {
        return given().spec(getRequestSpecWithAuth(role, createJobPayload))
                .when().post(CREATE_JOB_ENDPOINT);
    }
}