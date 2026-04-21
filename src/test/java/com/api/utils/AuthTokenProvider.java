package com.api.utils;

import com.api.constant.Roles;
import com.api.request.model.UserCredentials;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Log4j2
public class AuthTokenProvider {
    private AuthTokenProvider() {}
    private static final Map<Roles, String> tokenCache = new ConcurrentHashMap<>();

    @Step("Getting the Auth token for role")
    public static String getToken(Roles role) {
        log.info("Checking if the token for {} is present in the cache", role);
        if (tokenCache.containsKey(role)) {
            log.info("Token found for {}", role);
            return tokenCache.get(role);
        }
        log.info("Token not found, preparing new login request");

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
        log.info("{} token cached for future request", role);
        return token;
    }
}
