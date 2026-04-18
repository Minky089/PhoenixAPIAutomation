package com.api.tests;

import com.api.constant.Roles;
import com.api.request.model.JobSearch;
import com.api.services.JobService;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static org.hamcrest.Matchers.notNullValue;

public class JobSearchAPITest {
    private JobService jobService;
    private JobSearch jobSearchPayload;
    private static final String JOB_ID = "JOB_253564";

    @BeforeMethod(description = "Instantiating Job service object and creating Job Search payload")
    public void setup() {
        jobService = new JobService();
        jobSearchPayload = new JobSearch(JOB_ID);
    }

    @Test(description = "Verify if details API is giving correct response", groups = {"api", "regression", "smoke"})
    public void jobSearchAPITest() {
        jobService.jobSearch(Roles.FD, jobSearchPayload)
                .then()
                .spec(getResponseSpec_OK())
                .body("data", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/jobSearchResponseSchema-FD.json"));
    }
}
