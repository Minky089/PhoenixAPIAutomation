package com.api.test;

import com.api.constant.Roles;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

public class UserDetailsAPITest {
    @Test
    public void userDetailsTest() {
        given()
                .spec(requestSpecWithAuth(Roles.FD))
                .when()
                .get("userdetails")
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/userDetailsResponseSchema.json"));
    }
}
