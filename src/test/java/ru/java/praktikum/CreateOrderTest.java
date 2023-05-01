package ru.java.praktikum;
import data.CreateOrder;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static steps.CreateOrderStep.orderCreate;

@RunWith(Parameterized.class)
public class CreateOrderTest {


    private final List<String> color;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {List.of("BLACK", "GRAY")},
                {List.of("BLACK")},
                {List.of( "GRAY")},
                {List.of( "")}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void orderCreateTest(){

        CreateOrder order = new CreateOrder("Ольга", "Симакова", "Rozhdestvenskaya, 17 ", "Kurskaya", "+89034567812", 4, "2023-05-01", "Жду с нетерпением", color);
        orderCreate(order).then().statusCode(201).and().assertThat().body("track", notNullValue());
    } }

