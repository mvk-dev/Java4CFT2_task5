package task5.register.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task5.database.repository.TppProductRegisterRepository;
import task5.register.dto.RegisterDto;
import task5.utils.CheckResult;
import task5.utils.Checkable;
import task5.utils.ResponseStatus;

import java.util.List;

@Component
public class CheckTppProductRegistry implements Checkable<RegisterDto, CheckResult> {
    @Autowired
    private TppProductRegisterRepository repository;

    @Override
    public CheckResult apply(RegisterDto dto) {
        int count = repository.countProductIdAndType(dto.getInstanceId(), dto.getRegistryTypeCode());
        if (count > 0)
            return new CheckResult(ResponseStatus.BAD, List.of("Параметр registryTypeCode тип регистра = " + dto.getRegistryTypeCode()
                    + " уже существует для ЭП с ИД = " + dto.getInstanceId()));

        return new CheckResult(ResponseStatus.OK, null);
    }
}
