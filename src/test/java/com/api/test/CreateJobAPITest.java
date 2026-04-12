package com.api.test;

import com.api.constant.*;
import com.api.request.model.*;
import com.api.utils.DateTimeUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

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
    CustomerProduct customerProduct = new CustomerProduct(DateTimeUtil.getTimeWithDaysAgo(10), myImei, myImei, myImei, DateTimeUtil.getTimeWithDaysAgo(10), Product.NEXUS_2.getId(), Model.NEXUS_2_BLUE.getId());
    List<Problems> problemsList = new ArrayList<>(List.of(new Problems(Problem.POOR_BATTERY_LIFE.getId(), "Battery Die")));
    CreateJobPayload createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getId(), Platform.FRONT_DESK.getId(), WarrantyStatus.IN_WARRANTY.getId(), OEM.GOOGLE.getId(), customer, customerAddress, customerProduct, problemsList);

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
