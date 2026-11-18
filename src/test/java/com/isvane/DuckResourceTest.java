package com.isvane;

import com.isvane.dto.DuckTransactionRequest;
import com.isvane.dto.DuckTransactionResponse;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(DuckResource.class)
class DuckResourceTest {

    @InjectMock
    DuckService service;

    @Test
    void buy_whenSuccessful_returns200() {
        var request = new DuckTransactionRequest(3);
        var mockResponse = DuckTransactionResponse.ok("Bought 3 ducks", 3, 7);

        when(service.buy(request)).thenReturn(mockResponse);

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/buy")
        .then()
            .statusCode(200)
            .body("success", is(true))
            .body("message", is("Bought 3 ducks"))
            .body("userDucks", is(3))
            .body("storeDucks", is(7));
    }

    @Test
    void buy_whenServiceFails_returns400() {
        var request = new DuckTransactionRequest(50);
        var mockResponse = DuckTransactionResponse.error("Not enough ducks in store", 0, 10);

        when(service.buy(request)).thenReturn(mockResponse);

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/buy")
        .then()
            .statusCode(400)
            .body("success", is(false))
            .body("message", is("Not enough ducks in store"))
            .body("userDucks", is(0))
            .body("storeDucks", is(10));
    }

    @Test
    void buy_whenQuantityLessThanOne_returns400FromValidation() {
        var invalidRequest = new DuckTransactionRequest(0);

        given()
            .contentType(ContentType.JSON)
            .body(invalidRequest)
        .when()
            .post("/buy")
        .then()
            .statusCode(400);

        verifyNoInteractions(service);
    }

    @Test
    void sell_whenSuccessful_returns200() {
        var request = new DuckTransactionRequest(2);
        var mockResponse = DuckTransactionResponse.ok("Sold 2 ducks", 1, 9);

        when(service.sell(request)).thenReturn(mockResponse);

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/sell")
        .then()
            .statusCode(200)
            .body("success", is(true))
            .body("userDucks", is(1))
            .body("storeDucks", is(9));
    }

    @Test
    void sell_whenServiceFails_returns400() {
        var request = new DuckTransactionRequest(5);
        var mockResponse = DuckTransactionResponse.error("Not enough user ducks", 1, 9);

        when(service.sell(request)).thenReturn(mockResponse);

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/sell")
        .then()
            .statusCode(400)
            .body("success", is(false))
            .body("message", is("Not enough user ducks"));
    }
}
