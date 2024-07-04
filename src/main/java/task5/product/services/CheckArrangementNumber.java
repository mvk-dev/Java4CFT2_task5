package task5.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task5.database.repository.AgreementRepository;
import task5.product.dto.ProductArrangementDto;
import task5.product.dto.ProductDto;
import task5.utils.CheckResult;
import task5.utils.Checkable;
import task5.utils.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Component
public class CheckArrangementNumber implements Checkable<ProductDto, CheckResult> {
    @Autowired
    private AgreementRepository repository;

    @Override
    public CheckResult apply(ProductDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto.getInstanceArrangement() != null) {
            for (ProductArrangementDto arrangementDto : dto.getInstanceArrangement()) {
                List<String> idsList = repository.findDistinctProductIdByNumber(arrangementDto.getNumber());
                if (!idsList.isEmpty())
                    errors.add("Параметр № Дополнительного соглашения (сделки) Number = " + arrangementDto.getNumber() +
                            " уже существует для ЭП с ИД = " + String.join(",", idsList));
            }

            if (!errors.isEmpty())
                return new CheckResult(ResponseStatus.BAD, errors);
        }

        return new CheckResult(ResponseStatus.OK, null);
    }
}
