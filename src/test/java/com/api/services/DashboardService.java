package com.api.services;

import com.api.constant.Roles;
import com.api.utils.SpecUtil;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DashboardService {
    private static final String COUNT_ENDPOINT = "dashboard/count";
    private static final String DETAIL_ENDPOINT = "dashboard/details";

    public Response count(Roles role) {
        return given().spec(SpecUtil.getRequestSpecWithAuth(role))
                .when().get(COUNT_ENDPOINT);
    }

    public Response countWithNoAuth() {
        return given().spec(SpecUtil.getRequestSpec()).when().get(COUNT_ENDPOINT);
    }

    public Response details(Roles role, Object payload) {
        return given().spec(SpecUtil.getRequestSpecWithAuth(role))
                .body(payload)
                .when().post(DETAIL_ENDPOINT);
    }

    public Response detailsWithNoAuth() {
        return given().spec(SpecUtil.getRequestSpec()).when().post(DETAIL_ENDPOINT);
    }

}
