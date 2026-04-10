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

public class MasterAPITest {
    @Test
    public void masterAPITest() {
        Header authorizeHeader = new Header("Authorization", getToken(Roles.FD));
        given()
                .baseUri(ConfigManager.getProperty("BASE_URI"))
                .header(authorizeHeader)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all()
                .when()
                .post("master")
                .then()
                .log().all()
                .statusCode(200)
                .body("message", equalTo("Success"))
                .body("data", notNullValue())
                .body("data", hasKey("mst_oem"))
                .body("data", hasKey("mst_model"))
                .body("$", hasKey("message"))
                .body("$", hasKey("data"))
                .body("data.mst_oem.size()", greaterThanOrEqualTo(0))
                .body("data.mst_oem", everyItem(hasKey("id")))
                .body("data.mst_model.size()", greaterThan(0))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/masterResponseSchema.json"))
                .time(lessThan((long) 2000));
    }

    @Test
    public void masterAPITest_MissingAuthToken() {
        given()
                .baseUri(ConfigManager.getProperty("BASE_URI"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all()
                .when()
                .post("master")
                .then()
                .log().all()
                .statusCode(401);
    }
}
