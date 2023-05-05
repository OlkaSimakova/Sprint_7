package ru.java.praktikum;

import data.CourierCreate;
import data.CourierLogin;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierAssert;
import steps.CourierRandomizer;
import steps.CourierStep;

import static steps.CourierStep.courierLogin;


public class CourierCreateTest {
    protected final CourierRandomizer courierRandomizer = new CourierRandomizer();
    int courierId;
    private CourierStep courierStep;
    private CourierCreate courierCreate;
    private CourierAssert courierAssert;

    @Before
    @Step("Создание тестовых данных курьера")
    public void setUp() {
        courierStep = new CourierStep();
        courierCreate = courierRandomizer.createNewRandomCourier();
        courierAssert = new CourierAssert();
    }

    @After
    @Step("Удаление тестовых данных")
    public void deleteCourier() {
        if (courierId != 0) {
            courierStep.courierDelete(courierId);
        }
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void courierCreateTest() throws InterruptedException {
        ValidatableResponse responseCreateCourier = courierStep.createCourier(courierCreate);
        CourierLogin courierLogin =  CourierLogin.from(courierCreate);
        int courierId = courierStep.courierLogin(courierLogin).then().extract().path("id");
        courierAssert.createCourierOk(responseCreateCourier);

    }

    @Test
    @DisplayName("Ошибка при создании двух одинаковых курьеров")
    public void createSameCourier(){
        courierStep.createCourier(courierCreate);
        ValidatableResponse responseCreateCourier = courierStep.createCourier(courierCreate);
        courierAssert.createCourierSameLoginError(responseCreateCourier);
    }


    @Test
    @DisplayName("Ошибка при создании курьера без пароля")
    public void createCourierWithoutPass(){
        courierCreate.setPassword(null);
        ValidatableResponse responseNullPassword = courierStep.createCourier(courierCreate);
        courierAssert.createCourierError(responseNullPassword);
    }

    @Test
    @DisplayName("Ошибка при создании курьера без логина")
    public void createCourierWithoutLogin(){
        courierCreate.setLogin(null);
        ValidatableResponse responseNullLogin = courierStep.createCourier(courierCreate);
        courierAssert.createCourierError(responseNullLogin);
    }
}
