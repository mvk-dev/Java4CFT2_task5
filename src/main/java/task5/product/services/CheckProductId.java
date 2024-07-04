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
public class CheckProductId implements Checkable<ProductDto, CheckResult> {
    @Autowired
    private TppProductRepository repository;

    @Override
    public CheckResult apply(ProductDto dto) {
        long count = repository.countIdByInstanceId(dto.getInstanceId());

        if (count == 0)
            return new CheckResult(ResponseStatus.NOT_FOUND, List.of("Экземпляр продукта с параметром instanceId = " + dto.getInstanceId()
                    + " не найден"));

        return new CheckResult(ResponseStatus.OK, null);
    }
}
