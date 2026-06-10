package org.example.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.example.config.Config;

import static io.restassured.http.ContentType.JSON;

public class BaseApi {

    protected static String authToken;

    protected RequestSpecification getRequestSpec() {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(Config.getApiBaseUrl())
                .setContentType(JSON)
                .setAccept(JSON);

        if (authToken != null && !authToken.isEmpty()) {
            builder.addHeader("Authorization", "Bearer " + authToken);
        }

        return builder.build();
    }

    protected void setAuthToken(String token) {
        authToken = token;
    }

    protected void clearAuthToken() {
        authToken = null;
    }
}
