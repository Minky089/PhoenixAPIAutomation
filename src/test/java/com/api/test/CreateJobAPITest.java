package com.api.test;

import com.api.constant.Roles;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import pojo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.api.utils.SpecUtil.getRequestSpecWithAuth;
import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateJobAPITest {
    private final String myImei = new Random().ints(15, 0, 10).mapToObj(String::valueOf).collect(Collectors.joining());
    Customer customer = new Customer("Minky", "Nguyen", "7045663552", "", "minky0089@gmail.com", "");
    CustomerAddress customerAddress = new CustomerAddress("805", "Oakwood Heights", "Sunset Boulevard", "Grand Theater", "West End", 90210, "USA", "California");
    CustomerProduct customerProduct = new CustomerProduct("2026-03-02T17:00:00.000Z", myImei, myImei, myImei, "2026-03-05T17:00:00.000Z", 1, 1);
    List<Problems> problemsList = new ArrayList<>(List.of(new Problems(1, "Battery Die")));
    CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsList);

    @Test
    public void createJobAPITest() {
        given()
                .spec(getRequestSpecWithAuth(Roles.FD, createJobPayload))
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
