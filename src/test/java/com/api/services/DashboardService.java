package com.api.services;

import com.api.constant.Roles;
import com.api.utils.SpecUtil;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import static io.restassured.RestAssured.given;

@Log4j2
public class DashboardService {
    private static final String COUNT_ENDPOINT = "dashboard/count";
    private static final String DETAIL_ENDPOINT = "dashboard/details";

    private void  prepareRequestLog(String endpoint, Roles role){log.info("Preparing request for the : {} endpoint, with {} role", endpoint,  role);}
    private void  prepareNoAuthRequestLog(String endpoint){log.info("Preparing request without auth for the : {} endpoint.", endpoint);}

    @Step("Preparing Count API Request for the role")
    public Response count(Roles role) {
        prepareRequestLog(COUNT_ENDPOINT, role);
        return given().spec(SpecUtil.getRequestSpecWithAuth(role))
                .when().get(COUNT_ENDPOINT);
    }

    @Step("Preparing Count API Request without auth token")
    public Response countWithNoAuth() {
        prepareNoAuthRequestLog(COUNT_ENDPOINT);
        return given().spec(SpecUtil.getRequestSpec()).when().get(COUNT_ENDPOINT);
    }

    @Step("Preparing Details API Request")
    public Response details(Roles role, Object payload) {
        prepareRequestLog(DETAIL_ENDPOINT, role);
        return given().spec(SpecUtil.getRequestSpecWithAuth(role))
                .body(payload)
                .when().post(DETAIL_ENDPOINT);
    }

    public Response detailsWithNoAuth() {
        prepareNoAuthRequestLog(DETAIL_ENDPOINT);
        return given().spec(SpecUtil.getRequestSpec()).when().post(DETAIL_ENDPOINT);
    }

}
