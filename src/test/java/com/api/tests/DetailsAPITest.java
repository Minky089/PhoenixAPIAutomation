package com.api.tests;

import com.api.constant.Roles;
import com.api.request.model.Detail;
import com.api.services.DashboardService;
import com.api.utils.AuthTokenProvider;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static com.api.utils.SpecUtil.getResponseSpec_Text;
import static org.hamcrest.Matchers.*;

public class DetailsAPITest {
    private DashboardService dashboardService;
    private Detail detail;

    @BeforeMethod(description = "Instantiating Dashboard service object and creating Details payload")
    public void setup() {
        dashboardService = new DashboardService();
        detail = new Detail("created_today");
    }

    @Test(description = "Verify if details API is giving correct response", groups = {"api", "regression", "smoke"})
    public void detailAPITest() {
        dashboardService.details(Roles.FD, detail)
                .then()
                .spec(getResponseSpec_OK())
                .body("data", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/detailsResponseSchema-FD.json"));
    }

    @Test(description = "Verify if details API is giving correct status code for missing token", groups = {"api", "regression", "negative"})
    public void detailAPITest_MissingAuthToken() {
        dashboardService.detailsWithNoAuth()
                .then()
                .spec(getResponseSpec_Text(401));
    }
}