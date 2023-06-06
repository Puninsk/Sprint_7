import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

import static org.junit.Assert.assertEquals;

public class GetOrders {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void orderList() {

        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .when()// заполни body
                .get("/api/v1/orders")
                .then();
        assertEquals(HttpStatus.SC_OK, response.extract().statusCode());

    }
}
