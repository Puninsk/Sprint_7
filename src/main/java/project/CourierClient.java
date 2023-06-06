package project;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class CourierClient {

    public String BASE_URI = "http://qa-scooter.praktikum-services.ru/";
    public String PATH = "/api/v1/courier";
    public static String LOGIN_PATH = "/api/v1/courier/login";

    public CourierClient() {
        RestAssured.baseURI = BASE_URI;
    }

    public ValidatableResponse create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }

    public static ValidatableResponse login(CourierCreds creds) {
        return given()
                .header("Content-type", "application/json")
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    public static ValidatableResponse delete(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then();
     }

}
