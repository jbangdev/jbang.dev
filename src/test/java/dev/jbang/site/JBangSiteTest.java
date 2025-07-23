package dev.jbang.site;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkiverse.roq.testing.RoqAndRoll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

import static org.hamcrest.Matchers.containsString;

@QuarkusTest
@RoqAndRoll
public class JBangSiteTest {

    @Test
    public void testTree() {
        RestAssured.when().get("/tree")
                .then()
                .statusCode(200)
                .body(containsString(
                    "Thanks to Milen Dyankov"
                ));
    }

    @Test
    public void testDocumentation() {
        RestAssured.when().get("/documentation")
                .then()
                .statusCode(200);
    }

    @Test
    public void testFail() {
        RestAssured.when().get("/documentation")
                .then()
                .statusCode(404);
    }
} 