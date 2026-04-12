package com.api.test;

import com.api.constant.Roles;
import com.api.utils.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CountAPITest {
    @Test(description = "Verify if count API is giving correct response", groups = {"api", "regression", "smoke"})
    public void countAPITest() {
        given()
                .spec(getRequestSpecWithAuth(Roles.FD))
                .when()
                .get("dashboard/count")
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
        given()
                .baseUri(ConfigManager.getProperty("BASE_URI"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all()
                .when()
                .get("dashboard/count")
                .then()
                .spec(getResponseSpec_Text(401));
    }
}
