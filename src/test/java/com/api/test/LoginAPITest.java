package com.api.test;

import com.api.utils.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import pojo.UserCredentials;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginAPITest {

    @Test
    public void loginAPITest() {
        UserCredentials userCredentials = new UserCredentials("iamfd", "password");

        given()
                .baseUri(ConfigManager.getProperty("BASE_URI"))
                .and()
                .contentType(ContentType.JSON)
                .and()
                .accept(ContentType.JSON)
                .and()
                .body(userCredentials)
                .log().uri()
                .log().method()
                .log().headers()
                .log().body()
                .when()
                .post("login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .and().body("message", equalTo("Success"))
                .and().body("data.token", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/loginAPIResponseSchema.json"))
                .and()
                .time(lessThan((long)2000))
                .extract().response();
    }
}
