package com.api.utils;

import com.api.constant.Roles;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.*;

import com.api.request.model.UserCredentials;

import static io.restassured.RestAssured.*;

public class AuthTokenProvider {

    private AuthTokenProvider() {
    }

    public static String getToken(Roles role) {
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

        return given()
                .baseUri(ConfigManager.getProperty("BASE_URI"))
                .contentType(ContentType.JSON)
                .body(userCredentials)
                .when()
                .post("login")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("message", equalTo("Success"))
                .extract()
                .body()
                .jsonPath()
                .getString("data.token");
    }
}
