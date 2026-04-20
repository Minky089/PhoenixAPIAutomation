package com.api.tests;

import com.api.constant.Roles;
import com.api.services.MasterService;
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
@Feature("Master")
public class MasterAPITest {
    MasterService masterService;

    @BeforeMethod(description = "Instantiating Master service object")
    public void setup() {
        masterService = new MasterService();
    }

    @Story("Master should bring OEM details, Problem type, Warranty Status")
    @Description("Verify if the master API is giving correct response")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify if the master API is giving correct response", groups = {"api", "regression", "smoke"})
    public void masterAPITest() {
        masterService.master(Roles.FD)
                .then()
                .spec(getResponseSpec_OK())
                .body("data", notNullValue())
                .body("data", hasKey("mst_oem"))
                .body("data", hasKey("mst_model"))
                .body("$", hasKey("message"))
                .body("$", hasKey("data"))
                .body("data.mst_oem.size()", greaterThanOrEqualTo(0))
                .body("data.mst_oem", everyItem(hasKey("id")))
                .body("data.mst_model.size()", greaterThan(0))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/masterResponseSchema.json"));
    }

    @Story("Master should bring OEM details, Problem type, Warranty Status")
    @Description("Verify Master API returns 401 Unauthorized when no token provided")
    @Test(description = "Verify if master API is giving correct status code for missing token", groups = {"api", "regression", "negative"})
    public void masterAPITest_MissingAuthToken() {
        masterService.masterWithNoAuth()
                .then()
                .spec(getResponseSpec_Text(401));
    }
}
