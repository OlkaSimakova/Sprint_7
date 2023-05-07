package steps;

import data.CreateOrder;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static steps.RestAssuredClient.getBaseSpec;

public class CreateOrderStep {
    @Step("create order")
    public static Response orderCreate(CreateOrder order){
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post("/api/v1/orders");
    }
}
