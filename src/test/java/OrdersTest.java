import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class OrdersTest {
    private OrderClient orderClient;

    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Should be Return List Orders")
    public void shouldReturnListOrdersTest(){
        ValidatableResponse response = orderClient.get(null);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code incorrect", SC_OK, statusCode);

        List<Object> orders = response.extract().body().path("orders");
        assertNotNull("List orders is Null", orders);
        assertFalse("List orders is Empty", orders.isEmpty());
    }
}
