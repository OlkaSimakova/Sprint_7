package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static steps.RestAssuredClient.getBaseSpec;

public class CourierLogin {

    @Step("courier login")
    public static Response courierLogin(data.CourierLogin courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }
}
