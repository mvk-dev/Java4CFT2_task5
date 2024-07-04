package task5.product.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task5.product.dto.ProductDto;
import task5.product.dto.ResponseProductDto;
import task5.product.services.ProductOperator;
import task5.utils.CheckResult;
import task5.utils.ResponseStatus;

import java.util.List;

@RestController
@RequestMapping("/corporate-settlement-instance")
public class ProductController {
    private final ProductOperator operator;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(RequestEntity<ProductDto> request) {
        ProductDto dto = request.getBody();
        CheckResult checkResult = operator.validate(dto);
        if (checkResult.getStatus().getCode() != 0)
            return makeBadResponse(checkResult.getStatus().getCode(), checkResult.getErrors());

        ResponseProductDto responseProductDto;
        try {
            responseProductDto = operator.process(dto);
        } catch (Exception e) {
            return makeBadResponse(ResponseStatus.SERVER_ERR.getCode(), List.of(e.getMessage()));
        }

        return makeOkResponse(responseProductDto);
    }

    private ResponseEntity<String> makeBadResponse(int status, List<String> errorList) {
        String error = String.join(System.lineSeparator(), errorList);
        return ResponseEntity.status(status).body(error);
    }

    private ResponseEntity<String> makeOkResponse(ResponseProductDto dto) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        ObjectNode data = mapper.valueToTree(dto);

        root.set("data", data);
        return ResponseEntity.ok().body(root.toPrettyString());
    }

    @Autowired
    public ProductController(ProductOperator operator) {
        this.operator = operator;
    }
}
