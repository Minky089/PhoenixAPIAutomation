package com.api.test;

import com.api.constant.Roles;
import com.api.utils.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.utils.AuthTokenProvider.getToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CountAPITest {
    @Test
    public void countAPITest() {
        Header authorizeHeader = new Header("Authorization", getToken(Roles.FD));
        given()
                .baseUri(ConfigManager.getProperty("BASE_URI"))
                .header(authorizeHeader)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all()
                .when()
                .get("dashboard/count")
                .then()
                .log().all()
                .statusCode(200)
                .body("message", equalTo("Success"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/countResponseSchema-FD.json"))
                .time(lessThan((long) 2000))
                .body("data", notNullValue())
                .body("data.size()", equalTo(3))
                .body("data.count", everyItem(greaterThanOrEqualTo(0)))
                .body("data.label", everyItem(not(blankOrNullString())))
                .body("data.key", containsInAnyOrder("pending_fst_assignment", "pending_for_delivery", "created_today"));
    }

    @Test
    public void countAPITest_MissingAuthToken() {
        given()
                .baseUri(ConfigManager.getProperty("BASE_URI"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all()
                .when()
                .get("dashboard/count")
                .then()
                .log().all()
                .statusCode(401);
    }
}
