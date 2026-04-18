package com.api.tests;

import com.api.constant.Roles;
import com.api.services.UserService;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getResponseSpec_OK;

public class UserDetailsAPITest {
    @Test(description = "Verify if Userdetails API is giving correct response", groups = {"api", "regression", "smoke"})
    public void userDetailsTest() {
        UserService userService = new UserService();

        userService.userDetails(Roles.FD)
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/userDetailsResponseSchema.json"));
    }
}
