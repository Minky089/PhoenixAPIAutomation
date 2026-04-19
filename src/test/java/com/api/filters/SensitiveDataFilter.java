package com.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
public class SensitiveDataFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        log.info("********** REQUEST DETAILS ********** ");
        log.info("BASE URI: {}", requestSpec.getURI());
        log.info("HTTP METHOD: {}", requestSpec.getMethod());
        Response response = ctx.next(requestSpec, responseSpec);
        redactHeader(requestSpec);
        redactPayload(requestSpec);
        log.info("********** RESPONSE DETAILS ********** ");
        log.info("STATUS: {}", response.getStatusLine());
        log.info("RESPONSE TIME: {}", response.timeIn(TimeUnit.MILLISECONDS));
        log.info("RESPONSE HEADERS: \n {}", response.getHeaders());
        redactResponseBody(response);
        return response;
    }

    public void redactPayload(FilterableRequestSpecification requestSpec) {
        if (requestSpec.getBody() != null) {
            String requestPayload = requestSpec.getBody().toString(); // Print the Request Body in String format
            requestPayload = requestPayload.replaceAll("\"password\"\\s*:\\s*\"[^\"]+\"", "\"password\": \"[REDACTED]\"");
            log.info("Redacted request payload: {}", requestPayload);
        }
    }

    public void redactHeader(FilterableRequestSpecification requestSpec) {
        List<Header> headerList = requestSpec.getHeaders().asList();
        for (Header header : headerList) {
            String value = header.getName().equalsIgnoreCase("Authorization")
                    ? "\"[REDACTED]\""
                    : header.getValue();
            log.info("HEADER KEY {} : VALUE {}", header.getName(), value);
        }
    }

    private void redactResponseBody(Response response) {
        String responseBody = response.asPrettyString();
        responseBody = responseBody.replaceAll("\"token\"\\s*:\\s*\"[^\"]+\"", "\"token\": \"[REDACTED]\"");
        log.info("Redacted response body: {}", responseBody);
    }
}
