package com.api.services;

import com.api.utils.SpecUtil;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class AuthService {
    private static final String LOGIN_ENDPOINT = "login";

    public Response login(Object userCredentials) {
        return given().spec(SpecUtil.getRequestSpec(userCredentials))
                .when().post(LOGIN_ENDPOINT);
    }
}
