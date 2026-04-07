package com.api.test;

import com.restassured.demo.UserCredentials;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginAPITest {

    @Test
    public void loginAPITest() {
        UserCredentials userCredentials = new UserCredentials("iamfd", "password");

        given()
                .baseUri("http://64.227.160.186:9000/v1/")
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
