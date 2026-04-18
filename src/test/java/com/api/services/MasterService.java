package com.api.services;

import com.api.constant.Roles;
import com.api.utils.SpecUtil;
import io.restassured.response.Response;

import static com.api.utils.SpecUtil.getRequestSpecWithAuth;
import static io.restassured.RestAssured.given;

public class MasterService {
    private static final String MASTER_ENDPOINT = "master";

    public Response master(Roles role) {
        return given().spec(getRequestSpecWithAuth(role))
                .when().post(MASTER_ENDPOINT);
    }

    public Response masterWithNoAuth() {
        return given().when().spec(SpecUtil.getRequestSpec()).post(MASTER_ENDPOINT);
    }
}
