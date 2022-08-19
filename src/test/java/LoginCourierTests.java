import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class LoginCourierTests {
    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.getDefault();
        courierClient = new CourierClient();
        courierClient.create(courier).assertThat().statusCode(SC_CREATED);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().body().path("id");
    }

    @Test
    @DisplayName("Check can login in service with valid credentials")
    public void canBeLoginTest(){
        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.from(courier));
        int statusCode = responseLogin.extract().statusCode();
        assertEquals("Status code incorrect", SC_OK, statusCode);

        courierId = responseLogin.extract().body().path("id");
        assertNotNull("ID is incorrect", courierId);
    }

    @Test
    @DisplayName("Check not can login in service with empty Login")
    public void notCanBeLoginIsEmptyLoginReturnErrorMessageTest() {
        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.emptyLoginFrom(courier));
        int statusCode = responseLogin.extract().statusCode();
        assertEquals("Status code incorrect", SC_BAD_REQUEST, statusCode);

        String expectedErrorMessage = "Недостаточно данных для входа";
        String actualErrorMessage = responseLogin.extract().body().path("message");
        assertEquals("Error message incorrect", expectedErrorMessage, actualErrorMessage);
    }

    //В этом тесте ловим ошибку. Статус код 504 вместо ожидаемого 400
    @Test
    @DisplayName("Check not can login in service with empty Password")
    public void notCanBeLoginIsEmptyPasswordReturnErrorMessageTest() {
        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.emptyPassFrom(courier));
        int statusCode = responseLogin.extract().statusCode();
        assertEquals("Status code incorrect", SC_BAD_REQUEST, statusCode);

        String expectedErrorMessage = "Недостаточно данных для входа";
        String actualErrorMessage = responseLogin.extract().body().path("message");
        assertEquals("Error message incorrect", expectedErrorMessage, actualErrorMessage);
    }

    @Test
    @DisplayName("Check not can login in service with invalid Login")
    public void notCanBeLoginIsInvalidLoginReturnErrorMessageTest() {
        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.inValidLoginFrom(courier));
        int statusCode = responseLogin.extract().statusCode();
        assertEquals("Status code incorrect", SC_NOT_FOUND, statusCode);

        String expectedErrorMessage = "Учетная запись не найдена";
        String actualErrorMessage = responseLogin.extract().body().path("message");
        assertEquals("Error message incorrect", expectedErrorMessage, actualErrorMessage);
    }

    @Test
    @DisplayName("Check not can login in service with invalid Password")
    public void notCanBeLoginIsInvalidPasswordReturnErrorMessageTest() {
        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.inValidPassFrom(courier));
        int statusCode = responseLogin.extract().statusCode();
        assertEquals("Status code incorrect", SC_NOT_FOUND, statusCode);

        String expectedErrorMessage = "Учетная запись не найдена";
        String actualErrorMessage = responseLogin.extract().body().path("message");
        assertEquals("Error message incorrect", expectedErrorMessage, actualErrorMessage);
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }
}
