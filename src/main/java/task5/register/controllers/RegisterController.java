package task5.register.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task5.register.dto.RegisterDto;
import task5.register.dto.ResponseRegisterDto;
import task5.register.services.RegisterOperator;
import task5.utils.CheckResult;

import java.util.List;

@RestController
@RequestMapping("/corporate-settlement-account")
public class RegisterController {
    private final RegisterOperator registerOperator;

    @PostMapping("/create")
    public ResponseEntity<String> createRegister(RequestEntity<RegisterDto> request) {
        System.out.println(request);
        RegisterDto registerDto = request.getBody();
        System.out.println(registerDto);

        CheckResult checkResult = registerOperator.validate(registerDto);
        if (checkResult.getStatus().getCode() != 0)
            return makeBadResponse(checkResult.getStatus().getCode(), checkResult.getErrors());

        ResponseRegisterDto responseRegisterDto;
        try {
            responseRegisterDto = registerOperator.process(registerDto);
        } catch (Exception e) {
            return makeBadResponse(500, List.of(e.getMessage()));
        }

        return makeOkResponse(responseRegisterDto);
    }

    private ResponseEntity<String> makeBadResponse(int status, List<String> errorList) {
        String error = String.join(System.lineSeparator(), errorList);
        return ResponseEntity.status(status).body(error);
    }

    private ResponseEntity<String> makeOkResponse(ResponseRegisterDto dto) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        ObjectNode data = mapper.valueToTree(dto);

        root.set("data", data);
        return ResponseEntity.ok().body(root.toPrettyString());
    }

    @Autowired
    public RegisterController(RegisterOperator registerOperator) {
        this.registerOperator = registerOperator;
    }
}