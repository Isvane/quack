package com.isvane;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Quack! Quack! Quack!!"));
    }

    @Test
    void testGreetingEndpoint() {
        given()
            .when().get("/hello/greeting/Charlene")
            .then()
                .statusCode(200)
                .body(is("Quack " + "Charlene" + " Quack time = " + 1));
    }

}
