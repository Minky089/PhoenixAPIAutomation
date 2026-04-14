package com.api.tests.datadriven;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getRequestSpec;
import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static io.restassured.RestAssured.given;

public class LoginAPIDataDrivenTest {
    @Test(description = "Verify if login API is working for FD user",
          groups = {"api", "regression", "datadriven"},
          dataProviderClass = com.dataproviders.DataProvidersUtils.class,
          dataProvider = "LoginAPIDataProvider"
    )
    public void loginAPIDataDriverTest(UserBean userBean) {
        given().spec(getRequestSpec(userBean))
                .when()
                .post("login")
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/loginAPIResponseSchema.json"));
    }

    @Test(description = "Verify if login API is working for FD user",
            groups = {"api", "regression", "datadriven", "JSON"},
            dataProviderClass = com.dataproviders.DataProvidersUtils.class,
            dataProvider = "LoginAPIJSONDataProvider"
    )
    public void loginAPIJSONDataDriverTest(UserCredentials userCredentials) {
        given().spec(getRequestSpec(userCredentials))
                .when()
                .post("login")
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/loginAPIResponseSchema.json"));
    }
}