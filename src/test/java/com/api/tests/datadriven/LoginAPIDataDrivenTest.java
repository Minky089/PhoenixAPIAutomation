package com.api.tests.datadriven;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;
import com.listeners.APITestListener;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getResponseSpec_OK;

@Listeners(APITestListener.class)
@Epic("User Management")
@Feature("Data Driven Authentication")
public class LoginAPIDataDrivenTest {
    private AuthService authService;

    @BeforeMethod
    public void setUp() {
        authService = new AuthService();
    }

    @Story("Valid User should be able to login to the System")
    @Description("Verify if login API with data driven is working for FD user")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Verify if login API is working for FD user",
            groups = {"api", "regression", "datadriven"},
            dataProviderClass = com.dataproviders.DataProvidersUtils.class,
            dataProvider = "LoginAPIDataProvider"
    )
    public void loginAPIDataDriverTest(UserBean userBean) {
        authService.login(userBean)
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/loginAPIResponseSchema.json"));
    }

    @Story("Valid User should be able to login to the System")
    @Description("Verify if login API with JSON data driven is working for FD user")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Verify if login API is working for FD user",
            groups = {"api", "regression", "datadriven", "JSON"},
            dataProviderClass = com.dataproviders.DataProvidersUtils.class,
            dataProvider = "LoginAPIJSONDataProvider"
    )
    public void loginAPIJSONDataDriverTest(UserCredentials userCredentials) {
        authService.login(userCredentials)
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/loginAPIResponseSchema.json"));
    }
}