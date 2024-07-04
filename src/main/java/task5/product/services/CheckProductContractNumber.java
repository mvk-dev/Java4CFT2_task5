package task5.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task5.database.repository.TppProductRepository;
import task5.product.dto.ProductDto;
import task5.utils.CheckResult;
import task5.utils.Checkable;
import task5.utils.ResponseStatus;

import java.util.List;

@Component
public class CheckProductContractNumber implements Checkable<ProductDto, CheckResult> {
    @Autowired
    private TppProductRepository repository;

    @Override
    public CheckResult apply(ProductDto dto) {
        List<String> idsList = repository.findByNumber(dto.getContractNumber());

        if (!idsList.isEmpty())
            return new CheckResult(ResponseStatus.BAD, List.of("Параметр ContractNumber = " + dto.getContractNumber() +
                    " уже существует для ЭП с ИД = " + String.join(",", idsList)));

        return new CheckResult(ResponseStatus.OK, null);
    }
}
