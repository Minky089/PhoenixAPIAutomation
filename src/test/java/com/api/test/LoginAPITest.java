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
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userCredentials)
                .log().all()
                .when()
                .post("login")
                .then()
                .log().all()
                .statusCode(200)
                .body("message", equalTo("Success"))
                .body("data.token", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/loginAPIResponseSchema.json"))
                .time(lessThan((long) 2000))
                .extract().response();
    }
}
