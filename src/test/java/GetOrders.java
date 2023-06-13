import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

import static org.junit.Assert.assertEquals;

public class GetOrders {

    public static final String ORDERS_GET_PATH = "/api/v1/orders";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void orderList() {

        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .when()// заполни body
                .get(ORDERS_GET_PATH)
                .then();

        assertEquals(HttpStatus.SC_OK, response.extract().statusCode());

    }
}
