package com.api.services;

import com.api.constant.Roles;
import com.api.utils.SpecUtil;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserService {
    private static final String USERDETAILS_ENDPOINT = "userdetails";

    public Response userDetails(Object payload) {
        return given().spec(SpecUtil.getRequestSpecWithAuth(Roles.FD))
                .when().get(USERDETAILS_ENDPOINT);
    }
}
