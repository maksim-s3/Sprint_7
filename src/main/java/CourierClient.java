import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    public static final String COURIER_CREATING_PATCH = "/api/v1/courier";
    public static final String COURIER_LOGIN_PATCH = "/api/v1/courier/login";
    public static final String COURIER_DELETE_PATCH = "/api/v1/courier/";

    @Step
    @DisplayName("Create courier")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_CREATING_PATCH)
                .then();
    }

    @Step
    @DisplayName("Login courier in service")
    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_LOGIN_PATCH)
                .then();
    }

    @Step
    @DisplayName("Delete courier")
    public ValidatableResponse delete(int courierId){
        return given()
                .spec(getBaseSpec())
                .delete(COURIER_DELETE_PATCH+courierId)
                .then();
    }
}
