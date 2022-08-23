import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreateParametrizedTests {
    private Order order;
    private OrderClient orderClient;
    private int trackOrder;
    private static Faker faker = new Faker();

    public OrderCreateParametrizedTests(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] getOrder() {
        return new Object[][]{
                {new Order(faker.name().firstName(), faker.name().lastName(), faker.address().secondaryAddress(), "1", faker.phoneNumber().cellPhone(), 5, "2022-08-25", "Позвонить за 15мин", new String[]{ColorScooter.BLACK})},
                {new Order(faker.name().firstName(), faker.name().lastName(), faker.address().secondaryAddress(), "2", faker.phoneNumber().cellPhone(), 5, "2022-08-25", "Позвонить за 15мин", new String[]{ColorScooter.GREY})},
                {new Order(faker.name().firstName(), faker.name().lastName(), faker.address().secondaryAddress(), "2", faker.phoneNumber().cellPhone(), 5, "2022-08-25", "Позвонить за 15мин", new String[]{ColorScooter.BLACK, ColorScooter.GREY})},
                {new Order(faker.name().firstName(), faker.name().lastName(), faker.address().secondaryAddress(), "2", faker.phoneNumber().cellPhone(), 5, "2022-08-25", "Позвонить за 15мин", new String[]{})},
        };
    }

    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("An order can be created")
    public void canBeOrderCreateTest(){
        ValidatableResponse responseCreated = orderClient.create(order);

        int statusCode = responseCreated.extract().statusCode();
        assertEquals("Status code incorrect", SC_CREATED, statusCode);

        trackOrder = responseCreated.extract().body().path("track");
        assertNotNull("Track number not found", trackOrder);
    }

    @After
    public void tearDown(){
        orderClient.cancel(trackOrder);
    }
}
