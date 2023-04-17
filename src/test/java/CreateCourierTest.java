import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {

    int courierId;
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void createCourier() {
        CourierUser courierUser = new CourierUser("cocofive", "1234"); // создай объект, который соответствует JSON
        ValidatableResponse response = (ValidatableResponse) given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()// заполни body
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        ValidatableResponse responseLogin = (ValidatableResponse) given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()// заполни body
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200);

        courierId = responseLogin.extract().path("id");

    }



    @After
    public void cleanAll(){
        given()
                .when()
                .delete("/api/v1/courier/" + courierId);
    }


    @Test
    public void cannotCreateTwoSimularCourier() {
            CourierUser courierUser = new CourierUser("coconike", "1234"); // создай объект, который соответствует JSON
            ValidatableResponse response = given()
                    .header("Content-type", "application/json")
                    .body(courierUser)
                    .when()// заполни body
                    .post("/api/v1/courier")
                    .then()
                    .statusCode(201);

            ValidatableResponse responseSecondCourier = given()
                    .header("Content-type", "application/json")
                    .body(courierUser)
                    .when()
                    .post("/api/v1/courier")
                    .then()
                    .statusCode(409);
        responseSecondCourier.assertThat().body("message", equalTo("Этот логин уже используется"));

        courierId = response.extract().path("id");

        }


    @Test
    public void cannotCreateCourierWithNotAllRequairedFields() {
        CourierUser courierUser = new CourierUser("", "1234");
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400);
        response.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

        courierUser = new CourierUser("coco", "");
        response = given()
                .header("Content-type", "application/json")
                .body(courierUser)
                .when()// заполни body
                .post("/api/v1/courier")
                .then()
                .statusCode(400);
        response.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    }