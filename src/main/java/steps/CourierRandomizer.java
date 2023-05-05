package steps;

import com.github.javafaker.Faker;
import data.CourierCreate;
import io.qameta.allure.Step;

public class CourierRandomizer {
    static Faker faker = new Faker();

    public static final String NEW_LOGIN_FAKED = faker.name().username();
    @Step("Создание нового курьера с рандомными данными")
    public CourierCreate createNewRandomCourier() {
        return new CourierCreate(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName());
    }
}