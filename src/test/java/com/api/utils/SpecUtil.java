package com.api.utils;

import com.api.constant.Roles;
import com.api.filters.SensitiveDataFilter;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public class SpecUtil {
    private SpecUtil() {}

    @Step("Setting up the BaseURI, Content Type as Application/JSON and attaching the Sensitive Data filter")
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new SensitiveDataFilter())
                .addFilter(new AllureRestAssured())
                .build();
    }

    @Step("Setting up the BaseURI, Content Type as Application/JSON, attaching the Sensitive Data filter and attaching payload")
    public static RequestSpecification getRequestSpec(Object payload) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setBody(payload)
                .addFilter(new SensitiveDataFilter())
                .addFilter(new AllureRestAssured())
                .build();
    }

    @Step("Setting up the BaseURI, Content Type as Application/JSON and attaching the Sensitive Data filter for role")
    public static RequestSpecification getRequestSpecWithAuth(Roles role){
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", AuthTokenProvider.getToken(role))
                .addFilter(new SensitiveDataFilter())
                .addFilter(new AllureRestAssured())
                .build();
    }

    @Step("Setting up the BaseURI, Content Type as Application/JSON, attaching the Sensitive Data filter for a role and attaching payload")
    public static RequestSpecification getRequestSpecWithAuth(Roles role, Object payload) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", AuthTokenProvider.getToken(role))
                .setBody(payload)
                .addFilter(new SensitiveDataFilter())
                .addFilter(new AllureRestAssured())
                .build();
    }

    @Step("Validate the response to have Content Type as Application/JSON, Status Code 200 and response time less than 2000ms")
    public static ResponseSpecification getResponseSpec_OK(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectResponseTime(lessThan(2000L))
                .build();
    }

    @Step("Validate the response to have Content Type as Text, and response time less than 2000ms")
    public static ResponseSpecification getResponseSpec_Text(int statusCode){
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(2000L))
                .build();
    }
}
