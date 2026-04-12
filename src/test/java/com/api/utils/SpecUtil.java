package com.api.utils;

import com.api.constant.Roles;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public class SpecUtil {
    private SpecUtil() {}

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getRequestSpec(Object payload) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setBody(payload)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getRequestSpecWithAuth(Roles role){
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", AuthTokenProvider.getToken(role))
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getRequestSpecWithAuth(Roles role, Object payload) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", AuthTokenProvider.getToken(role))
                .setBody(payload)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec_OK(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectResponseTime(lessThan(2000L))
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec_Text(int statusCode){
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(2000L))
                .log(LogDetail.ALL)
                .build();
    }

}
