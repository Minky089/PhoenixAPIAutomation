package com.api.test;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserDetailsAPITest {
    @Test
    public void UserDetailsTest() {
        Header authorizeHeader = new Header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NCwiZmlyc3RfbmFtZSI6ImZkIiwibGFzdF9uYW1lIjoiZmQiLCJsb2dpbl9pZCI6ImlhbWZkIiwibW9iaWxlX251bWJlciI6Ijg4OTk3NzY2NTUiLCJlbWFpbF9pZCI6Im1hcmtAZ21haWwuY29tIiwicGFzc3dvcmQiOiI1ZjRkY2MzYjVhYTc2NWQ2MWQ4MzI3ZGViODgyY2Y5OSIsInJlc2V0X3Bhc3N3b3JkX2RhdGUiOm51bGwsImxvY2tfc3RhdHVzIjowLCJpc19hY3RpdmUiOjEsIm1zdF9yb2xlX2lkIjo1LCJtc3Rfc2VydmljZV9sb2NhdGlvbl9pZCI6MSwiY3JlYXRlZF9hdCI6IjIwMjEtMTEtMDNUMDg6MDY6MjMuMDAwWiIsIm1vZGlmaWVkX2F0IjoiMjAyMS0xMS0wM1QwODowNjoyMy4wMDBaIiwicm9sZV9uYW1lIjoiRnJvbnREZXNrIiwic2VydmljZV9sb2NhdGlvbiI6IlNlcnZpY2UgQ2VudGVyIEEiLCJpYXQiOjE3NzU1NjYzODF9.iwoCcmOj757vPzaBIiYgR6mr5cZnCA3NSJD9v5QYN1U");
                given()
                        .baseUri("http://64.227.160.186:9000/v1/")
                        .and()
                        .header(authorizeHeader)
                        .contentType(ContentType.JSON)
                        .and()
                        .accept(ContentType.JSON)
                        .and()
                        .log().uri()
                        .log().method()
                        .log().body()
                        .log().headers()
                        .when()
                        .get("userdetails")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .and().body("message", equalTo("Success"))
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/userDetailsResponseSchema.json"))
                        .and()
                        .time(lessThan((long)2000));
    }
}
