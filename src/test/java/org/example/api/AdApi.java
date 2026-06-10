package org.example.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * API для работы с объявлениями.
 * Реальные эндпоинты:
 * - Создание: POST /api/create-listing (multipart/form-data)
 * - Получение списка: GET /api/listings/{page}
 * - Получение одного: GET /api/listing/{id}
 * - Обновление: PATCH /api/update-offer/{id} (multipart/form-data)
 * - Удаление: DELETE /api/listings/{id}
 */
public class AdApi extends BaseApi {

    private static final String CREATE_PATH = "/create-listing";
    private static final String LISTINGS_PATH = "/listings/";
    private static final String LISTING_PATH = "/listing/";
    private static final String UPDATE_PATH = "/update-offer/";
    private static final String DELETE_PATH = "/listings/";

    /**
     * Создаёт объявление через multipart/form-data.
     *
     * @param name        название
     * @param description описание
     * @param price       цена (строка)
     * @param category    категория (Авто, Книги, Садоводство, Хобби, Технологии)
     * @param condition   состояние (Новый / Б/У)
     * @param city        город (Москва, Санкт-Петербург)
     * @param token       JWT-токен авторизованного пользователя
     * @return Response с ID созданного объявления
     */
    public Response createAd(String name, String description, String price,
                             String category, String condition, String city,
                             String token) {
        return given()
                .baseUri(org.example.config.Config.getApiBaseUrl())
                .header("Authorization", "Bearer " + token)
                .multiPart("name", name)
                .multiPart("description", description)
                .multiPart("price", price)
                .multiPart("category", category)
                .multiPart("condition", condition)
                .multiPart("city", city)
                .when()
                .post(CREATE_PATH)
                .then()
                .extract()
                .response();
    }

    /**
     * Получает объявление по ID.
     */
    public Response getAd(String adId) {
        return given()
                .spec(getRequestSpec())
                .when()
                .get(LISTING_PATH + adId)
                .then()
                .extract()
                .response();
    }

    /**
     * Удаляет объявление по ID.
     */
    public Response deleteAd(String adId, String token) {
        return given()
                .baseUri(org.example.config.Config.getApiBaseUrl())
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(DELETE_PATH + adId)
                .then()
                .extract()
                .response();
    }
}
