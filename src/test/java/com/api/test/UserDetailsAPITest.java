package com.api.test;

import static com.api.utils.AuthTokenProvider.*;

import com.api.constant.Roles;
import com.api.utils.OldConfigManager;
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
                        .baseUri(OldConfigManager.getProperty("BASE_URI"))
                        .and()
                        .header(authorizeHeader)
                        .contentType(ContentType.JSON)
                        .and()
                        .accept(ContentType.JSON)
                        .and()
                        .log().uri()
                        .log().method()
                        .log().body()
                        .log().headers()
                        .when()
                        .get("userdetails")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .and().body("message", equalTo("Success"))
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/userDetailsResponseSchema.json"))
                        .and()
                        .time(lessThan((long)2000));
    }
}
