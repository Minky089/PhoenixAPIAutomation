package com.api.utils;

import com.api.constant.Roles;
import com.api.request.model.UserCredentials;
import io.restassured.http.ContentType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthTokenProvider {
    private AuthTokenProvider() {}
    private static final Map<Roles, String> tokenCache = new ConcurrentHashMap<>();

    public static String getToken(Roles role) {
        if (tokenCache.containsKey(role)) {
            return tokenCache.get(role);
        }
        UserCredentials userCredentials = null;
        if (role == Roles.FD) {
            userCredentials = new UserCredentials("iamfd", "password");
        } else if (role == Roles.ENG) {
            userCredentials = new UserCredentials("iameng", "password");
        } else if (role == Roles.QC) {
            userCredentials = new UserCredentials("iamqc", "password");
        } else if (role == Roles.SUP) {
            userCredentials = new UserCredentials("iamsup", "password");
        }

        String token = given().baseUri(ConfigManager.getProperty("BASE_URI"))
                .contentType(ContentType.JSON).body(userCredentials)
                .when().post("login")
                .then().log().ifValidationFails()
                .statusCode(200).body("message", equalTo("Success"))
                .extract().body().jsonPath().getString("data.token");

        tokenCache.put(role, token);
        return token;
    }
}
