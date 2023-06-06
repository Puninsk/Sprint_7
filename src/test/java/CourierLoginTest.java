import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.Courier;
import project.CourierClient;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

import static project.CourierCreds.credsFrom;

import static org.junit.Assert.assertEquals;
import io.qameta.allure.junit4.DisplayName;


public class CourierLoginTest {

    private CourierClient courierClient;
    private Courier courier;

    private int courierId;

    boolean trueStatement;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Courier authorization Successful")
    public void courierSuccessfulAuthorization() {
        courier = new Courier("kiki", "1234", "qwqw");
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
        assertEquals(HttpStatus.SC_OK, loginResponse.extract().statusCode());
    }
            @Test
            @DisplayName("Successful authorization shows id")
            public void courierAuthorizationIdCheck () {
            courier = new Courier("Kiki", "1234", "qwqw");
            ValidatableResponse response = courierClient.create(courier);
            ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
            loginResponse.assertThat().body("id", notNullValue());
        }

            @Test
            @DisplayName("Authorization without password")
            public void courierAuthorizeWithoutPasssword () {
            courier = new Courier("Kiki", "1234", "qwqw");
            ValidatableResponse response = courierClient.create(courier);
            courier.setPassword("");
            ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
            loginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
            trueStatement = true;
        }

            @Test
            @DisplayName("Authorization without login")
            public void authorizationWithoutLogin () {
            courier = new Courier("Kiki", "1234", "qwqw");
            ValidatableResponse response = courierClient.create(courier);
            courier.setLogin("");
            ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
            loginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
            trueStatement = true;
        }


            @Test
            @DisplayName("Authorization with invalid courier")
            public void сourierAuthorization () {
            courier = new Courier("Kik", "1234", "qwqw");
            ValidatableResponse response = courierClient.create(courier);
            assertEquals(HttpStatus.SC_CREATED, response.extract().statusCode());
            ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
            courierId = loginResponse.extract().path("id");
            courierClient.delete(courierId);
            ValidatableResponse loginResponse2 = CourierClient.login(credsFrom(courier));
            assertEquals(HttpStatus.SC_NOT_FOUND, loginResponse2.extract().statusCode());
            trueStatement = true;
        }

            @After
            public void cleanAll () {
                if (trueStatement != true) {
                ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
                courierId = loginResponse.extract().path("id");
                courierClient.delete(courierId);
            }
        }
        }