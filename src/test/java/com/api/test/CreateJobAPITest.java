package com.api.test;

import com.api.constant.Roles;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import pojo.*;

import static com.api.utils.SpecUtil.getRequestSpecWithAuth;
import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateJobAPITest {
    Customer customer = new Customer("Minky", "Nguyen", "7045663552", "", "minky0089@gmail.com", "");
    CustomerAddress customerAddress = new CustomerAddress("805", "Oakwood Heights", "Sunset Boulevard", "Grand Theater", "West End", 90210, "USA", "California");
    CustomerProduct customerProduct = new CustomerProduct("2026-03-02T17:00:00.000Z", "353242199446763", "353242199446763", "353242199446763", "2026-03-05T17:00:00.000Z", 1, 1);
    Problems problems = new Problems(1, "Battery Die");
    Problems[] problemsArray = new Problems[]{problems};
    CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsArray);

    @Test
    public void createJobAPITest() {
        given()
                .spec(getRequestSpecWithAuth(Roles.FD, createJobPayload))
                .when()
                .post("job/create")
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/createJobResponseSchema.json"))
                .body("data", notNullValue());
    }
}
