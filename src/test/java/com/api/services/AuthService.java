package com.api.services;

import com.api.filters.SensitiveDataFilter;
import com.api.request.model.UserCredentials;
import com.api.utils.SpecUtil;
import com.dataproviders.api.bean.UserBean;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import static io.restassured.RestAssured.given;

@Log4j2
public class AuthService {
    private static final String LOGIN_ENDPOINT = "login";

    private void prepareRequestLog(String username) {
        log.info("Preparing login request for the user: {}", username);
    }

    public Response login(Object userCredentials) {
        if (userCredentials instanceof UserCredentials) {
            prepareRequestLog(((UserCredentials) userCredentials).username());
        } else if (userCredentials instanceof UserBean) {
            prepareRequestLog(((UserBean) userCredentials).getUsername());
        }

        return given().filter(new SensitiveDataFilter())
                .spec(SpecUtil.getRequestSpec(userCredentials))
                .when().post(LOGIN_ENDPOINT);
    }
}
