package com.api.tests;

import com.api.constant.Roles;
import com.api.services.DashboardService;
import com.api.services.MasterService;
import com.api.utils.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CountAPITest {
    DashboardService dashboardService;

    @BeforeMethod(description = "Instantiating Dashboard service object")
    public void setup() {
        dashboardService = new DashboardService();
    }

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

    @Test(description = "Verify if count API is giving correct status code for missing token", groups = {"api", "regression", "negative"})
    public void countAPITest_MissingAuthToken() {
        dashboardService.countWithNoAuth()
                .then()
                .spec(getResponseSpec_Text(401));
    }
}
