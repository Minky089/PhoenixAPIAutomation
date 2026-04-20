package com.api.tests;

import com.api.constant.Roles;
import com.api.request.model.Detail;
import com.api.services.DashboardService;
import com.listeners.APITestListener;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static com.api.utils.SpecUtil.getResponseSpec_Text;
import static org.hamcrest.Matchers.notNullValue;

@Listeners(APITestListener.class)
@Epic("Job Management")
@Feature("Job Details")
public class DetailsAPITest {
    private DashboardService dashboardService;
    private Detail detail;

    @BeforeMethod(description = "Instantiating Dashboard service object and creating Details payload")
    public void setup() {
        dashboardService = new DashboardService();
        detail = new Detail("created_today");
    }

    @Story("Job Details should be shown")
    @Description("Verify if the Job Details API is shown correctly for FD user")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify if details API is giving correct response", groups = {"api", "regression", "smoke"})
    public void detailAPITest() {
        dashboardService.details(Roles.FD, detail)
                .then()
                .spec(getResponseSpec_OK())
                .body("data", notNullValue());
    }

    @Story("Job Details should be shown")
    @Description("Verify Job Details API returns 401 Unauthorized when no token provided")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify if details API is giving correct status code for missing token", groups = {"api", "regression", "negative"})
    public void detailAPITest_MissingAuthToken() {
        dashboardService.detailsWithNoAuth()
                .then()
                .spec(getResponseSpec_Text(401));
    }
}