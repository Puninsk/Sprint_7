import io.qameta.allure.Step;
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
        creatingCourierForAuthorization();
        courierAuthorization();
    }

    @Step("Creating a courier")
    public void creatingCourierForAuthorization() {
        courier = new Courier("Kiki", "1234", "qwqw");
        ValidatableResponse response = courierClient.create(courier);
    }

    @Step("Courier authorization")
    public void courierAuthorization() {
        ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
        loginResponse.statusCode(200).assertThat().body("id", notNullValue());
    }


    @Test
    @DisplayName("Successful authorization shows id")
    public void courierAuthorizationIdCheck () {
        creatingCourier();
        loginCourier();
    }

    @Step("Creating a courier")
    public void creatingCourier() {
        courier = new Courier("Kiki", "1234", "qwqw");
        ValidatableResponse response = courierClient.create(courier);
    }

    @Step("Checking the ID")
    public void loginCourier() {
        ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
        loginResponse.statusCode(200).assertThat().body("id", notNullValue());
    }

            @Test
            @DisplayName("Authorization without password")
            public void courierAuthorizeWithoutPasssword () {
            trueStatement = true;
            creatingCourierWithoutPassword();
            loginCourierWithoutPassword();
        }

    @Step("Creating a courier")
    public void creatingCourierWithoutPassword() {
        courier = new Courier("Kiki", "1234", "qwqw");
        ValidatableResponse response = courierClient.create(courier);
        courier.setPassword("");
    }

    @Step("Login with courier without password")
    public void loginCourierWithoutPassword() {
        ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
        loginResponse.statusCode(400).assertThat().body("message",equalTo("Недостаточно данных для входа"));
    }

            @Test
            @DisplayName("Authorization without login")
            public void authorizationWithoutLogin () {
            trueStatement = true;
            creatingCourierWithoutLogin();
            loginCourierWithoutLogin();
        }

    @Step("Creating a courier")
    public void creatingCourierWithoutLogin() {
        courier = new Courier("Kiki", "1234", "qwqw");
        ValidatableResponse response = courierClient.create(courier);
        courier.setLogin("");
    }

    @Step("Login with courier without login")
    public void loginCourierWithoutLogin() {
        ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
        loginResponse.statusCode(400).assertThat().body("message",equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Authorization with invalid courier")
    public void сourierAuthorization () {
        trueStatement = true;
        courier = new Courier("Kik", "1234", "qwqw");
        ValidatableResponse loginResponse = CourierClient.login(credsFrom(courier));
        assertEquals(HttpStatus.SC_NOT_FOUND, loginResponse.extract().statusCode());
        loginResponse.statusCode(404).assertThat().body("message",equalTo("Учетная запись не найдена"));
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