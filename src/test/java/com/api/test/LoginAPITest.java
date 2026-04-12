package com.api.test;

import org.testng.annotations.Test;
import com.api.request.model.UserCredentials;

import static com.api.utils.SpecUtil.getRequestSpec;
import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static io.restassured.RestAssured.given;

public class LoginAPITest {

    @Test
    public void loginAPITest() {
        UserCredentials userCredentials = new UserCredentials("iamfd", "password");
        given().spec(getRequestSpec(userCredentials))
                .when()
                .post("login")
                .then()
                .spec(getResponseSpec_OK());
    }
}
