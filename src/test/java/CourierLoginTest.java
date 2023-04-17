import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import io.qameta.allure.junit4.DisplayName;


public class CourierLoginTest {

    int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void courierAuthorize() {
        CourierUser courierUser = new CourierUser("cocofive", "1234");
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()
                .post("/api/v1/courier")
                .then();

        ValidatableResponse responseLogin = given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200);
        responseLogin.assertThat().body("id", notNullValue());
    }

    @Test
    public void requiredFieldsForAuthorization() {
        CourierUser courierUser = new CourierUser("cocofive", "1234");
        courierUser.setLogin("");
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400);

        response.assertThat().body("message", equalTo("Недостаточно данных для входа"));

        courierUser.setLogin("cocofive");
        courierUser.setPassword("");
        response = given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400);

        response.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void incorrectLoginOrPasswordLogInDeined() {
        CourierUser courierUser = new CourierUser("cocofive", "wrongPassword");
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404);
        response.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void requiredFieldIsMissing() {
        CourierUser courierUser = new CourierUser("", "1234");
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400);

        response.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void errorWithNoUser() {
        CourierUser courierUser = new CourierUser("nonexistent", "password");
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404);

        response.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }


    @Test
    public void canLoginCourier() {
        CourierUser courierUser = new CourierUser("cocofive", "1234");
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200);

        int courierId = response.extract().path("id");
        assertTrue(courierId > 0);
    }

    @After
    public void cleanAll(){
        given()
                .when()
                .delete("/api/v1/courier/" + courierId);
    }

}
