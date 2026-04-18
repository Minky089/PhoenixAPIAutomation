package com.api.services;

import com.api.constant.Roles;
import com.api.utils.SpecUtil;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DashboardService {
    private static final String COUNT_ENDPOINT = "dashboard/count";

    public Response count(Roles role) {
        return given().spec(SpecUtil.getRequestSpecWithAuth(role))
                .when().get(COUNT_ENDPOINT);
    }

    public Response countWithNoAuth() {
        return given().spec(SpecUtil.getRequestSpec()).when().get(COUNT_ENDPOINT);
    }
}
