package com.api.test;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.api.request.model.UserCredentials;

import static com.api.utils.SpecUtil.getRequestSpec;
import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static io.restassured.RestAssured.given;

public class LoginAPITest {
    private UserCredentials userCredentials;

    @BeforeMethod(description = "Create payload for Login API")
    public void setUp(){
        userCredentials = new UserCredentials("iamfd", "password");
    }

    @Test(description = "Verify if login API is working for FD user", groups = {"api", "regression", "smoke"})
    public void loginAPITest() {

        given().spec(getRequestSpec(userCredentials))
                .when()
                .post("login")
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/loginAPIResponseSchema.json"));
    }
}
