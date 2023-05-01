package ru.java.praktikum;

import data.CourierCreate;
import data.CourierLogin;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static steps.CourierLoginStep.courierLogin;
import static steps.CreateCourierStep.createCourier;
import static steps.DeleteCourierStep.courierDelete;

public class CourierCreateTest {

    @Test
    @DisplayName("Успешное создание курьера")
    public void courierCreateTest(){
        String login = RandomStringUtils.randomAlphabetic(7);
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(11);

        CourierCreate courier = new CourierCreate(login, password, firstName);
        boolean actual = createCourier(courier).then().statusCode(201).extract().body().path("ok");
        assertEquals(true, actual);

        //удаление данных о созданном курьере
        CourierLogin courierLog = new CourierLogin(login, password);
        int responseId = courierLogin(courierLog).then().extract().path("id");

        courierDelete(responseId);
    }

    @Test
    @DisplayName("Ошибка при создании двух одинаковых курьеров")
    public void createSameCourier(){
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(10);

        //создание первого курьера
        CourierCreate courier = new CourierCreate(login, password, firstName);
        createCourier(courier);
        //логин первого курьера, получение id
        CourierLogin courierLog = new CourierLogin(login, password);
        int responseId = courierLogin(courierLog).then().extract().path("id");
        //создание второго курьера с теми же данными
        CourierCreate sameCourier = new CourierCreate(login, password, firstName);
        createCourier(sameCourier).then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

        //удаление созданного курьера
        courierDelete(responseId);
    }


    @Test
    @DisplayName("Ошибка при создании курьера без пароля")
    public void createCourierWithoutPass(){
        String login = RandomStringUtils.randomAlphabetic(7);
        String firstName = RandomStringUtils.randomAlphabetic(11);
        CourierCreate courier = new CourierCreate(login, "", firstName);
        createCourier(courier).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ошибка при создании курьера без логина")
    public void createCourierWithoutLogin(){
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(11);
        CourierCreate courier = new CourierCreate("", password, firstName);
        createCourier(courier).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}
