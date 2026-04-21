package com.api.services;

import com.api.constant.Roles;
import com.api.utils.SpecUtil;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import static io.restassured.RestAssured.given;

@Log4j2
public class UserService {
    private static final String USERDETAILS_ENDPOINT = "userdetails";

    @Step("Preparing UserDetails API request")
    public Response userDetails(Object payload) {
        log.info("Preparing request for the : {} endpoint.", USERDETAILS_ENDPOINT);
        return given().spec(SpecUtil.getRequestSpecWithAuth(Roles.FD))
                .when().get(USERDETAILS_ENDPOINT);
    }
}
