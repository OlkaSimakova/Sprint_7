package steps;

import data.CourierCreate;
import data.CourierLogin;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static steps.RestAssuredClient.getBaseSpec;

public class CourierStep {
    @Step("create courier")
    public static ValidatableResponse createCourier(CourierCreate courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier").then();
    }

    @Step("courier login")
    public static Response courierLogin(CourierLogin courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }
    @Step("delete courier")
    public static void courierDelete(int responseId){
        given()
                .spec(getBaseSpec())
                .delete("/api/v1/courier/{id}", responseId);
    }

}
