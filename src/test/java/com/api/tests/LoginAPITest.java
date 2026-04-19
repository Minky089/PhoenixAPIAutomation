package com.api.tests;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;
import com.listeners.APITestListener;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getResponseSpec_OK;

@Listeners(APITestListener.class)
public class LoginAPITest {
    private UserCredentials userCredentials;
    private AuthService authService;

    @BeforeMethod(description = "Create payload for Login API")
    public void setUp() {
        userCredentials = new UserCredentials("iamfd", "password");
        authService = new AuthService();
    }

    @Test(description = "Verify if login API is working for FD user", groups = {"api", "regression", "smoke"})
    public void loginAPITest() {

        authService.login(userCredentials)
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/loginAPIResponseSchema.json"));
    }
}
