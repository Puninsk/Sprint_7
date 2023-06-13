
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import project.OrdersClient;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String color;
    public static final String ORDERS_POST_PATH = "/api/v1/orders";

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
                .post(ORDERS_POST_PATH)
                .then();

        response.statusCode(201).assertThat().body("track", notNullValue());

    }
}

