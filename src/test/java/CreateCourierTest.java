import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.Courier;
import project.CourierClient;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static project.CourierCreds.credsFrom;


public class CreateCourierTest {

    int courierId;

    private CourierClient courierClient;
    private Courier courier;
    boolean trueStatement;


    @Test
    @DisplayName("Create courier success")
    public void courierCreateTest() {
        courier = new Courier("Ninaa", "1234", "qwqw");
        ValidatableResponse response = courierClient.create(courier);
        assertEquals(HttpStatus.SC_CREATED, response.extract().statusCode());
    }

    @Test
    @DisplayName("Create courier success right message")
    public void courierCreateTestRightMessage() {
        courier = new Courier("Ninaa", "1234", "qwqw");
        ValidatableResponse response = courierClient.create(courier);
        response.assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create courier with not all fields")
    public void courierCreateTestNotAllFields() {
        courier = new Courier("Ninaa", "", "");
        ValidatableResponse response = courierClient.create(courier);
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.extract().statusCode());
        trueStatement = true;
    }

    @Test
    @DisplayName("Cannot create courier with the same name")
    public void courierCreateSameName() {
        courier = new Courier("Ninaa", "1234", "qwqw");
        ValidatableResponse response = courierClient.create(courier);
        assertEquals(HttpStatus.SC_CREATED, response.extract().statusCode());
        ValidatableResponse responseTwo = courierClient.create(courier);
        assertEquals(HttpStatus.SC_CONFLICT, responseTwo.extract().statusCode());
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
            if (trueStatement != true) {
            ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
            courierId = loginResponse.extract().path("id");
                courierClient.delete(courierId);
        }
    }

}
