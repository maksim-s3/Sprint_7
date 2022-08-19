import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestClient {
    public RequestSpecification getBaseSpec(){
        return given()
                .baseUri("http://qa-scooter.praktikum-services.ru")
                .header("Content-type", "application/json");
    }
}
