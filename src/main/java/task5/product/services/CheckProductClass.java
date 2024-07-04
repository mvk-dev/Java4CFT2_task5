package task5.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task5.database.repository.TppRefProductRegisterTypeRepository;
import task5.product.dto.ProductDto;
import task5.utils.CheckResult;
import task5.utils.Checkable;
import task5.utils.ResponseStatus;

import java.util.List;

@Component
public class CheckProductClass implements Checkable<ProductDto, CheckResult> {
    @Autowired
    private TppRefProductRegisterTypeRepository repository;

    @Override
    public CheckResult apply(ProductDto dto) {
        int count = repository.countByProductCodeAccountType(dto.getProductCode(), "Клиентский");

        if (count == 0)
            return new CheckResult(ResponseStatus.NOT_FOUND, List.of("КодПродукта = " + dto.getProductCode() +
                    " не найден в Каталоге продуктов tpp_ref_product_class"));

        return new CheckResult(ResponseStatus.OK, null);
    }
}
