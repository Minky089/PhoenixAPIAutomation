package com.api.tests;

import com.api.constant.Roles;
import com.api.services.UserService;
import com.listeners.APITestListener;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getResponseSpec_OK;

@Listeners(APITestListener.class)
@Epic("User Management")
@Feature("User Details")
public class UserDetailsAPITest {
    @Story("UserDetails should be shown")
    @Description("Verify if the UserDetails API is shown correctly")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify if Userdetails API is giving correct response", groups = {"api", "regression", "smoke"})
    public void userDetailsTest() {
        UserService userService = new UserService();

        userService.userDetails(Roles.FD)
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/userDetailsResponseSchema.json"));
    }
}
