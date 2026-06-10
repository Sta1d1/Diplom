package org.example.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.example.config.Config;

import static io.restassured.http.ContentType.JSON;

public class BaseApi {

    protected RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Config.getApiBaseUrl())
                .setContentType(JSON)
                .setAccept(JSON)
                .build();
    }
}
