import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.*;

public class CreatingCourierTests {
    private Courier courier;
    private Courier courierIsInvalid;
    private Courier courierDoubleLogin;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getDefault();
    }

    @Test
    @DisplayName("Check can create courier")
    public void courierCanBeCreatedTest() {
        ValidatableResponse responseCreated = courierClient.create(courier);
        int statusCode = responseCreated.extract().statusCode();
        assertEquals("Status code incorrect", SC_CREATED, statusCode);

        boolean isCreated = responseCreated.extract().body().path("ok");
        assertTrue("The response message is incorrect", isCreated);

        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.from(courier));
        courierId = responseLogin.extract().body().path("id");
        assertNotNull("ID is incorrect", courierId);
    }

    @Test
    @DisplayName("Check not can create courier with invalid Login")
    public void courierNotCanBeCreatedIsInvalidLoginReturnErrorMessageTest() {
        courierIsInvalid = CourierGenerator.getIsInvalidLogin();
        ValidatableResponse responseCreated = courierClient.create(courierIsInvalid);
        int statusCode = responseCreated.extract().statusCode();
        assertEquals("Status code incorrect", SC_BAD_REQUEST, statusCode);

        String expectedErrorMessage = "Недостаточно данных для создания учетной записи";
        String actualErrorMessage = responseCreated.extract().body().path("message");
        assertEquals("Text error message incorrect", expectedErrorMessage, actualErrorMessage);
    }

    @Test
    @DisplayName("Check not can create courier with invalid Password")
    public void courierNotCanBeCreatedIsInvalidPassReturnErrorMessageTest() {
        courierIsInvalid = CourierGenerator.getIsInvalidPass();
        ValidatableResponse responseCreated = courierClient.create(courierIsInvalid);
        int statusCode = responseCreated.extract().statusCode();
        assertEquals("Status code incorrect", SC_BAD_REQUEST, statusCode);

        String expectedErrorMessage = "Недостаточно данных для создания учетной записи";
        String actualErrorMessage = responseCreated.extract().body().path("message");
        assertEquals("Text error message incorrect", expectedErrorMessage, actualErrorMessage);
    }

    @Test
    @DisplayName("Check not can create double courier")
    public void courierNotCanBeCreatedDoubleCourierReturnErrorMessageTest() {
        ValidatableResponse responseCreated = courierClient.create(courier);
        boolean isCreated = responseCreated.extract().body().path("ok");
        assertTrue("The response message is incorrect", isCreated);

        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.from(courier));
        courierId = responseLogin.extract().body().path("id");

        ValidatableResponse responseCreatedDouble = courierClient.create(courier);
        String expectedErrorMessage = "Этот логин уже используется. Попробуйте другой.";
        String actualErrorMessage = responseCreatedDouble.extract().body().path("message");
        assertEquals("Text error message incorrect", expectedErrorMessage, actualErrorMessage);
    }

    @Test
    @DisplayName("Check not can create courier with recurring login")
    public void courierNotCanBeCreatedDoubleLoginReturnErrorMessageTest() {
        courierDoubleLogin = CourierGenerator.getDoubleLogin();

        ValidatableResponse responseCreated = courierClient.create(courier);
        boolean isCreated = responseCreated.extract().body().path("ok");
        assertTrue("The response message is incorrect", isCreated);

        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.from(courier));
        courierId = responseLogin.extract().body().path("id");

        ValidatableResponse responseCreatedDoubleLogin = courierClient.create(courierDoubleLogin);
        String expectedErrorMessage = "Этот логин уже используется. Попробуйте другой.";
        String actualErrorMessage = responseCreatedDoubleLogin.extract().body().path("message");
        assertEquals("Text error message incorrect", expectedErrorMessage, actualErrorMessage);
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

}
