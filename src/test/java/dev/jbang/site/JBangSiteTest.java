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
                .statusCode(200)
                .body(containsString(
                    "The page you requested has been relocated to"
                ));
    }

    /* 
     * Tests added as it broke when migrating to Quarkus ROQ new versions.
     */
    @Test
    public void testDocumentationStyles() {
        RestAssured.when().get("/documentation/_/css/site.css")
                .then()
                .statusCode(200);

        RestAssured.when().get("/documentation/_/js/site.js")
                .then()
                .statusCode(200);

        RestAssured.when().get("/documentation/_/js/sitenotfound.js")
                .then()
                .statusCode(404);


    }
} 