package task5.register.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task5.database.repository.TppRefProductRegisterTypeRepository;
import task5.register.dto.RegisterDto;
import task5.utils.CheckResult;
import task5.utils.Checkable;
import task5.utils.ResponseStatus;

import java.util.List;

@Component
public class CheckRegistryTypeCode implements Checkable<RegisterDto, CheckResult> {
    @Autowired
    private TppRefProductRegisterTypeRepository repository;

    @Override
    public CheckResult apply(RegisterDto dto) {
        int count = repository.countRegisterType(dto.getRegistryTypeCode());

        if (count == 0)
            return new CheckResult(ResponseStatus.NOT_FOUND, List.of("Код Продукта = " + dto.getRegistryTypeCode()
                    + " не найден в Каталоге продуктов tpp_ref_product_register_type для данного типа Регистра"));

        return new CheckResult(ResponseStatus.OK, null);
    }
}
