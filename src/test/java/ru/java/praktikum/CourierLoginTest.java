package ru.java.praktikum;
import data.CourierCreate;
import data.CourierLogin;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static steps.CourierLogin.courierLogin;
import static steps.CourierStep.createCourier;
import static steps.DeleteCourierStep.courierDelete;


public class CourierLoginTest {

    private Integer responseId = null;

    @Before
    public void setUp() {
        String login = RandomStringUtils.randomAlphabetic(7);
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(11);
        CourierCreate courier = new CourierCreate(login, password, firstName);
        createCourier(courier);
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void successCourierLoginTest() {
        //создание курьера
        String login = RandomStringUtils.randomAlphabetic(7);
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(11);
        CourierCreate courier = new CourierCreate(login, password, firstName);
        createCourier(courier);

        CourierLogin courierLog = new CourierLogin(login, password);
        courierLogin(courierLog).then().statusCode(200);
        responseId = courierLogin(courierLog).then().extract().path("id");
        assertNotNull(responseId);
    }

    //если какого-то поля нет, запрос возвращает ошибку
    @Test
    @DisplayName("Ошибка при авторизации курьера без логина")
    public void courierLoginWithoutLogin() {
        CourierLogin courier = new CourierLogin("", "123456");
        courierLogin(courier).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ошибка при авторизации курьера без пароля")
    public void courierLoginWithoutPass() {
        CourierLogin courier = new CourierLogin("Simakov", "");
        courierLogin(courier).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    //система вернёт ошибку, если неправильно указать логин или пароль
    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("Ошибка при авторизации курьера под неверными учетными данными")
    public void courierLoginWithInvalidCredentials() {
        CourierLogin courier = new CourierLogin("Simakov", "4567890");
        courierLogin(courier).then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @After
    @Step("Удаление курьера")
    public void CourierDelete() {

        if (responseId != null) {
            courierDelete(responseId);
        }
    }
}