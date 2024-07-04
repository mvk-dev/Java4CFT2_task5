package task5;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.charset.Charset;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CreateRegisterTests {
    private static final String postAddress = "corporate-settlement-account/create";

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

    @LocalServerPort
    private int port;

    @Test
    public void should_Return_400_When_RequestIsEmpty() {
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body("")
                .when()
                .post(postAddress)
                .then()
                .statusCode(400);
    }

    @Test
    public void should_Return_400_When_MandatoryFieldsEmpty() throws IOException {
        String inputJson = new ClassPathResource("register/register_empty_instanceId.json")
                .getContentAsString(Charset.defaultCharset());

        Response response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(inputJson)
                .when()
                .post(postAddress)
                .then()
                .extract().response();

        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals(response.asPrettyString(), "Имя обязательного параметра InstanceId не заполнено");
    }

    @Test
    public void should_Return_404_When_RegisterTypeCodeNotFound() throws IOException {
        String inputJson = new ClassPathResource("register/register_wrong_type_code.json")
                .getContentAsString(Charset.defaultCharset());

        Response response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(inputJson)
                .when()
                .post(postAddress)
                .then()
                .extract().response();

        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    public void should_Return_200_When_SendCorrectRequest() throws IOException {
        String inputJson = new ClassPathResource("register/register_correct_request.json")
                .getContentAsString(Charset.defaultCharset());

        Response response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(inputJson)
                .when()
                .post(postAddress)
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.asPrettyString());
        Assertions.assertTrue(node.has("data"));
        Assertions.assertTrue(node.get("data").has("accountId"));
        Assertions.assertNotNull(node.get("data").get("accountId").numberValue());
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        TestUtils.configureProperties(registry, postgres);
    }

}
