package com.api.services;

import com.api.constant.Roles;
import com.api.utils.SpecUtil;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import static com.api.utils.SpecUtil.getRequestSpecWithAuth;
import static io.restassured.RestAssured.given;

@Log4j2
public class MasterService {
    private static final String MASTER_ENDPOINT = "master";

    @Step("Preparing Master API request")
    public Response master(Roles role) {
        log.info("Preparing request for the : {} endpoint, with {} role", MASTER_ENDPOINT,  role);
        return given().spec(getRequestSpecWithAuth(role))
                .when().post(MASTER_ENDPOINT);
    }

    @Step("Preparing Master API request without auth token")
    public Response masterWithNoAuth() {
        log.info("Preparing request without auth for the : {} endpoint.", MASTER_ENDPOINT);
        return given().when().spec(SpecUtil.getRequestSpec()).post(MASTER_ENDPOINT);
    }
}
