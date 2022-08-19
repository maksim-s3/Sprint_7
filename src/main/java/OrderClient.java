import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    public static final String ORDER_CREATING_PATCH = "/api/v1/orders";
    public static final String ORDER_ACCEPT_PATCH = "/api/v1/orders/accept/";
    public static final String ORDER_CANCEL_PATCH = "/api/v1/orders/cancel";
    public static final String ORDERS_GET_LIST_PATCH = "/api/v1/orders?courierId=";

    @Step
    @DisplayName("Create order")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_CREATING_PATCH)
                .then();
    }

    @Step
    @DisplayName("Cancel order")
    public ValidatableResponse cancel(int trackOrder){
        return given()
                .spec(getBaseSpec())
                .body(String.format("{\n" +
                        "    \"track\": %s\n" +
                        "}", trackOrder))
                .when()
                .put(ORDER_CANCEL_PATCH)
                .then();
    }

    @Step
    @DisplayName("Get list orders")
    public ValidatableResponse get(String courierId){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_GET_LIST_PATCH)
                .then();
    }
}
