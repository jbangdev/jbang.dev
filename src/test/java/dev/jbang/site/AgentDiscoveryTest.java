package dev.jbang.site;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkiverse.roq.testing.RoqAndRoll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

import io.restassured.parsing.Parser;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.is;

/**
 * Tests for agent discovery metadata:
 * - Content Signals in robots.txt
 * - API catalog (RFC 9727)
 * - Agent Skills discovery index
 * - HTML link elements for discovery relations
 */
@QuarkusTest
@RoqAndRoll
public class AgentDiscoveryTest {

    // --- robots.txt Content Signals ---

    @Test
    public void testRobotsTxtExists() {
        RestAssured.when().get("/robots.txt")
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @Test
    public void testRobotsTxtHasContentSignals() {
        RestAssured.when().get("/robots.txt")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body(containsString("Content-Signal:"))
                .body(containsString("ai-train="))
                .body(containsString("search="))
                .body(containsString("ai-input="));
    }

    // --- API Catalog (RFC 9727) ---

    @Test
    public void testApiCatalogExists() {
        RestAssured.when().get("/.well-known/api-catalog")
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @Test
    public void testApiCatalogHasLinkset() {
        RestAssured.given().config(RestAssured.config().jsonConfig(
                        io.restassured.config.JsonConfig.jsonConfig()))
                .when().get("/.well-known/api-catalog")
                .then()
                .log().ifValidationFails()
                .using().defaultParser(Parser.JSON)
                .statusCode(200)
                .body("linkset", hasSize(greaterThanOrEqualTo(1)))
                .body("linkset[0].anchor", notNullValue());
    }

    @Test
    public void testApiCatalogHasServiceDoc() {
        RestAssured.given().config(RestAssured.config().jsonConfig(
                        io.restassured.config.JsonConfig.jsonConfig()))
                .when().get("/.well-known/api-catalog")
                .then()
                .log().ifValidationFails()
                .using().defaultParser(Parser.JSON)
                .statusCode(200)
                .body("linkset[0].'service-doc'", hasSize(greaterThanOrEqualTo(1)))
                .body("linkset[0].'service-doc'[0].href", notNullValue());
    }

    // --- Agent Skills Discovery Index ---

    @Test
    public void testAgentSkillsIndexExists() {
        RestAssured.when().get("/.well-known/agent-skills/index.json")
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @Test
    public void testAgentSkillsIndexHasSchema() {
        RestAssured.when().get("/.well-known/agent-skills/index.json")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("'$schema'", containsString("agentskills.io"));
    }

    @Test
    public void testAgentSkillsIndexHasSkills() {
        RestAssured.when().get("/.well-known/agent-skills/index.json")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("skills", hasSize(greaterThanOrEqualTo(1)))
                .body("skills.name", hasItems("jbang", "jbang-scripting"));
    }

    @Test
    public void testAgentSkillsHaveRequiredFields() {
        RestAssured.when().get("/.well-known/agent-skills/index.json")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("skills.name", everyItem(notNullValue()))
                .body("skills.type", everyItem(notNullValue()))
                .body("skills.description", everyItem(notNullValue()))
                .body("skills.url", everyItem(notNullValue()))
                .body("skills.digest", everyItem(startsWith("sha256:")));
    }

    // --- HTML <link> elements for agent discovery ---

    @Test
    public void testHomepageHasApiCatalogLink() {
        RestAssured.when().get("/")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body(containsString("rel=\"api-catalog\""))
                .body(containsString("/.well-known/api-catalog"));
    }

    @Test
    public void testHomepageHasServiceDocLink() {
        RestAssured.when().get("/")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body(containsString("rel=\"service-doc\""));
    }
}
