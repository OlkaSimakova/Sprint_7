package ru.java.praktikum;
import data.CourierCreate;
import data.CourierLogin;
import data.CreateOrder;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static steps.AcceptOrderStep.orderAccept;
import static steps.CourierStep.courierLogin;
import static steps.CourierStep.createCourier;
import static steps.CreateOrderStep.orderCreate;
import static steps.GetOrderListStep.getOrderList;


public class GetListOfOrdersTest {

    @Test
    @DisplayName("Список заказов")
    public void getOrderListTest(){
        //создание курьера
        String login = RandomStringUtils.randomAlphabetic(7);
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(11);
        CourierCreate courier = new CourierCreate(login, password, firstName);
        createCourier(courier);

        //логин курьера, получение id
        CourierLogin courierLog = new CourierLogin(login, password);
        int courierId = courierLogin(courierLog).then().extract().path("id");

        //создание заказа, получение id
        CreateOrder order = new CreateOrder("Ольга", "Симакова", "Rozhdestvenskaya, 17 ", "Kurskaya", "+89034567812", 4, "2023-05-01", "Жду с нетерпением", List.of("BLACK", "GRAY"));
        int orderId = orderCreate(order).then().extract().path("track");

        //принять заказ
        orderAccept(orderId, courierId);

        //проверить список заказов
        getOrderList().then().assertThat().body("orders", notNullValue()).and().body(not(equalTo("error")));
    }
}