package com.api.tests;

import com.api.constant.Roles;
import com.api.services.DashboardService;
import com.listeners.APITestListener;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Listeners(APITestListener.class)
@Epic("Job Management")
@Feature("Job Count")
public class CountAPITest {
    DashboardService dashboardService;

    @BeforeMethod(description = "Instantiating Dashboard service object")
    public void setup() {
        dashboardService = new DashboardService();
    }

    @Story("Job Count should be shown")
    @Description("Verify if the Job Count API is shown correctly for FD user")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify if count API is giving correct response", groups = {"api", "regression", "smoke"})
    public void countAPITest() {
        dashboardService.count(Roles.FD)
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/countResponseSchema-FD.json"))
                .body("data", notNullValue())
                .body("data.size()", equalTo(3))
                .body("data.count", everyItem(greaterThanOrEqualTo(0)))
                .body("data.label", everyItem(not(blankOrNullString())))
                .body("data.key", containsInAnyOrder("pending_fst_assignment", "pending_for_delivery", "created_today"));
    }

    @Story("Job Count should be shown")
    @Description("Verify Job Count API returns 401 Unauthorized when no token provided")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify if count API is giving correct status code for missing token", groups = {"api", "regression", "negative"})
    public void countAPITest_MissingAuthToken() {
        dashboardService.countWithNoAuth()
                .then()
                .spec(getResponseSpec_Text(401));
    }
}
