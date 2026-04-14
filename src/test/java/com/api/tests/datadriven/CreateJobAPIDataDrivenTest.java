package com.api.tests.datadriven;

import com.api.constant.Roles;
import com.api.request.model.CreateJobPayload;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getRequestSpecWithAuth;
import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class CreateJobAPIDataDrivenTest {
    /*@Test(description = "Verify if create job API is able to create Inwarranty job",
          groups = {"api", "regression", "datadriven"},
          dataProviderClass = com.dataproviders.DataProvidersUtils.class,
          dataProvider = "CreateJobAPIDataProvider"
    )*/
    //Temporary comment out because the imei in CSV files need to be updated manually
    public void createJobAPITest(CreateJobPayload createJobPayload) {
        given().spec(getRequestSpecWithAuth(Roles.FD, createJobPayload))
                .when()
                .post("job/create")
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/createJobResponseSchema.json"))
                .body("message", equalTo("Job created successfully. "))
                .body("data.mst_service_location_id", equalTo(1))
                .body("data.job_number", startsWith("JOB_"));
    }

    @Test(description = "Verify if create job API is able to create with fake data generator",
            groups = {"api", "regression", "datadriven"},
            dataProviderClass = com.dataproviders.DataProvidersUtils.class,
            dataProvider = "CreateJobAPIFakerDataProvider")
    public void createJobAPITestWithFakeData(CreateJobPayload createJobPayload) {
        given().spec(getRequestSpecWithAuth(Roles.FD, createJobPayload))
                .when()
                .post("job/create")
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/createJobResponseSchema.json"))
                .body("message", equalTo("Job created successfully. "))
                .body("data.mst_service_location_id", equalTo(1))
                .body("data.job_number", startsWith("JOB_"));
    }
}