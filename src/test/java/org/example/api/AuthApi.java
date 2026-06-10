package org.example.api;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthApi extends BaseApi {

    private static final String TOKEN_PATH = "token.access_token";

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

        String token = response.jsonPath().getString("access_token.access_token");
        setAuthToken(token);
        return token;
    }

    /**
     * Авторизация пользователя через API.
     * POST /api/signin возвращает { "token": { "access_token": "eyJ..." } }.
     */
    public String login(String email, String password) {
        Response response = given()
                .spec(getRequestSpec())
                .body(Map.of(
                        "email", email,
                        "password", password
                ))
                .when()
                .post("/signin")
                .then()
                .extract()
                .response();

        String token = response.jsonPath().getString(TOKEN_PATH);
        setAuthToken(token);
        return token;
    }
}
