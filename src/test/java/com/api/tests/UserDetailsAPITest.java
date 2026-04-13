package com.api.tests;

import com.api.constant.Roles;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static com.api.utils.SpecUtil.getRequestSpecWithAuth;
import static io.restassured.RestAssured.given;

public class UserDetailsAPITest {
    @Test(description = "Verify if Userdetails API is giving correct response", groups = {"api", "regression", "smoke"})
    public void userDetailsTest() {
        given()
                .spec(getRequestSpecWithAuth(Roles.FD))
                .when()
                .get("userdetails")
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/userDetailsResponseSchema.json"));
    }
}
