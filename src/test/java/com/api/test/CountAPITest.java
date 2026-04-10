package com.api.test;

import com.api.constant.Roles;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.utils.AuthTokenProvider.getToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class CountAPITest {
    @Test
    public void countAPITest() {
        Header authorizeHeader = new Header("Authorization", getToken(Roles.FD));
        given()
                .baseUri("http://64.227.160.186:9000/v1/")
                .and()
                .header(authorizeHeader)
                .contentType(ContentType.JSON)
                .and()
                .accept(ContentType.JSON)
                .and()
                .log().uri()
                .log().body()
                .log().headers()
                .log().method()
                .when()
                .get("dashboard/count")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .and().body("message", equalTo("Success"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/countResponseSchema.json"))
                .and()
                .time(lessThan((long) 2000))
                .extract()
                .jsonPath().getString("data.first_name");
    }
}
