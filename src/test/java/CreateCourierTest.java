import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.Courier;
import project.CourierClient;

import static org.hamcrest.Matchers.equalTo;
import static project.CourierCreds.credsFrom;

public class CreateCourierTest {

    int courierId;

    private CourierClient courierClient;
    private Courier courier;
    boolean trueStatement;


    @Test
    @DisplayName("Create courier success right message")
    public void courierCreateTestRightMessage() {
        courier = new Courier("Ninaa", "1234", "qwqw");
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(201).assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create courier with not all fields")
    public void courierCreateTestNotAllFields() {
        trueStatement = true;
        courier = new Courier("Ninaa", "", "");
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Cannot create courier with the same name")
    public void courierCreateSameName() {
        creatingCourierFirst();
        creatingCourierSecond();
    }

    @Step("Creating a courier")
    public void creatingCourierFirst() {
        courier = new Courier("Ninaa", "1234", "qwqw");
        ValidatableResponse response = courierClient.create(courier);
    }

    @Step("Creating a second courier")
    public void creatingCourierSecond() {
        ValidatableResponse responseTwo = courierClient.create(courier);
        responseTwo.statusCode(409).assertThat().body("message", equalTo("Этот логин уже используется"));
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
