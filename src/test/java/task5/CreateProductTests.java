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
public class CreateProductTests {
    private static final String postAddress = "corporate-settlement-instance/create";

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

    @LocalServerPort
    private int port;

    @Test
    public void should_Return_400_When_MandatoryFieldsEmpty() throws IOException {
        String inputJson = new ClassPathResource("product/product_empty_mandatory_fields.json")
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
        String error = response.asPrettyString();
        Assertions.assertTrue(error.matches("^Имя обязательного параметра \\w+ не заполнено$"));
    }

    @Test
    public void should_Return_400_When_DuplicatedProductNumber() throws IOException {
        String inputJson = new ClassPathResource("product/product_duplicate_number.json")
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
        String error = response.asPrettyString();
        Assertions.assertTrue(error.matches("^Параметр ContractNumber = \\w+ уже существует для ЭП с ИД = \\w+"));
    }

    @Test
    public void should_Return_400_When_DuplicatedAgreementNumber() throws IOException {
        String inputJson = new ClassPathResource("product/product_duplicate_agreement_number.json")
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
        String error = response.asPrettyString();
        Assertions.assertTrue(error.matches("^Параметр № Дополнительного соглашения \\(сделки\\) Number = .+"));
    }

    @Test
    public void should_Return_404_When_ProductClassNotExists() throws IOException {
        String inputJson = new ClassPathResource("product/product_check_product_class.json")
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
        String error = response.asPrettyString();
        Assertions.assertTrue(error.matches("^КодПродукта = .+ не найден в Каталоге продуктов tpp_ref_product_class"));
    }

    @Test
    public void should_Return_404_When_ProductInstanceIdNotFound() throws IOException {
        String inputJson = new ClassPathResource("product/product_empty_instance_id.json")
                .getContentAsString(Charset.defaultCharset());

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(inputJson)
                .when()
                .post(postAddress)
                .then()
                .statusCode(404);
    }

    @Test
    public void should_Return_200_When_SendCorrectRequest() throws IOException {
        String inputJson = new ClassPathResource("product/product_correct_request.json")
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

        JsonNode dataNode = node.get("data");
        Assertions.assertTrue(dataNode.has("instanceId"));
        Assertions.assertNotNull(dataNode.get("instanceId").numberValue());

        Assertions.assertTrue(dataNode.has("registerId"));
        Assertions.assertTrue(dataNode.has("supplementaryAgreementId"));
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        TestUtils.configureProperties(registry, postgres);
    }
}
