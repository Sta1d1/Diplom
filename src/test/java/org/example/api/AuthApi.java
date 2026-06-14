package org.example.api;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthApi extends BaseApi {

    /**
     * Регистрация нового пользователя через API.
     * POST /api/signup возвращает { "access_token": { "access_token": "eyJ..." } }.
     */
    public String register(String email, String password) {
        Response response = given()
                .spec(getRequestSpec())
                .body(Map.of(
                        "email", email,
                        "password", password,
                        "submitPassword", password
                ))
                .when()
                .post("/signup")
                .then()
                .statusCode(201)
                .extract()
                .response();

        return response.jsonPath().getString("access_token.access_token");
    }
}
