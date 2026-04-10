package com.api.test;

import static com.api.utils.AuthTokenProvider.*;

import com.api.constant.Roles;
import com.api.utils.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class UserDetailsAPITest {
    @Test
    public void userDetailsTest() {
        Header authorizeHeader = new Header("Authorization", getToken(Roles.FD));
        given()
                        .baseUri(ConfigManager.getProperty("BASE_URI"))
                        .header(authorizeHeader)
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .log().all()
                        .when()
                        .get("userdetails")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("message", equalTo("Success"))
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/userDetailsResponseSchema.json"))
                        .time(lessThan((long)2000));
    }
}
