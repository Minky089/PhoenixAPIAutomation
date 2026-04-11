package com.api.test;

import com.api.constant.Roles;
import com.api.utils.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MasterAPITest {
    @Test
    public void masterAPITest() {
        given()
                .spec(requestSpecWithAuth(Roles.FD))
                .when()
                .post("master")
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
                .spec(getResponseSpec_Text(401));
    }
}
