package task5.register.services;

import org.springframework.stereotype.Component;
import task5.register.dto.RegisterDto;
import task5.utils.CheckResult;
import task5.utils.Checkable;
import task5.utils.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Component
public class CheckRegisterRequestBody implements Checkable<RegisterDto, CheckResult> {
    @Override
    public CheckResult apply(RegisterDto dto) {
        List<String> errors = new ArrayList<>();
        CheckResult checkResult = new CheckResult(ResponseStatus.OK, errors);
        String template = "Имя обязательного параметра <param> не заполнено";

        if (dto.getInstanceId() == null || dto.getInstanceId() == 0) {
            errors.add(template.replace("<param>", "InstanceId"));
        }

        if (!errors.isEmpty())
            checkResult.setStatus(ResponseStatus.BAD);
        return checkResult;
    }
}
