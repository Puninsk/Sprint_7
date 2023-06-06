
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import project.OrdersClient;
import static io.restassured.RestAssured.given;


import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String color;

    public CreateOrderTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"GREY" + "," + "BLACK"},
                {""},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }


    @Test
    public void cannotCreateTwoSimularCourier() {
        OrdersClient ordersClient = new OrdersClient("Naruto", "Uchiha", "Konoha, 142 apt.",
                "4", "+7 800 355 35 35", "5", "2020-06-06", "Saske, come back to Konoha", color); // создай объект, который соответствует JSON
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(ordersClient)
                .when()// заполни body
                .post("/api/v1/orders")
                .then();

        assertEquals(HttpStatus.SC_CREATED, response.extract().statusCode());

        response.assertThat().body("track", notNullValue());
    }
}

