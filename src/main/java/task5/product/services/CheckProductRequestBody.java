package task5.product.services;

import task5.product.dto.ProductArrangementDto;
import task5.product.dto.ProductDto;
import task5.utils.CheckResult;
import task5.utils.Checkable;
import task5.utils.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

public class CheckProductRequestBody implements Checkable<ProductDto, CheckResult> {
    @Override
    public CheckResult apply(ProductDto dto) {
        List<String> errors = new ArrayList<>();
        CheckResult checkResult = new CheckResult(ResponseStatus.OK, errors);
        String template = "Имя обязательного параметра <param> не заполнено";

        if (dto.getProductType().isEmpty())
            errors.add(template.replace("<param>", "productType"));

        if (dto.getProductCode().isEmpty())
            errors.add(template.replace("<param>", "productCode"));

        if (dto.getRegisterType().isEmpty())
            errors.add(template.replace("<param>", "registerType"));

        if (dto.getMdmCode().isEmpty())
            errors.add(template.replace("<param>", "mdmCode"));

        if (dto.getContractNumber().isEmpty())
            errors.add(template.replace("<param>", "contractNumber"));

        if (dto.getContractDate() == null)
            errors.add(template.replace("<param>", "contractDate"));

        if (dto.getPriority() == null)
            errors.add(template.replace("<param>", "priority"));

        if (dto.getContractId() == null)
            errors.add(template.replace("<param>", "contractId"));

        if (dto.getBranchCode().isEmpty())
            errors.add(template.replace("<param>", "BranchCode"));

        if (dto.getIsoCurrencyCode().isEmpty())
            errors.add(template.replace("<param>", "IsoCurrencyCode"));

        if (dto.getUrgencyCode().isEmpty())
            errors.add(template.replace("<param>", "urgencyCode"));

        if (dto.getUrgencyCode().isEmpty())
            errors.add(template.replace("<param>", "urgencyCode"));


        if (dto.getInstanceArrangement() != null && !dto.getInstanceArrangement().isEmpty()) {
            for (ProductArrangementDto arrangementDto : dto.getInstanceArrangement()) {
                if (arrangementDto.getNumber() == null)
                    errors.add(template.replace("<param>", "Arrangement Number"));
                if (arrangementDto.getOpeningDate() == null)
                    errors.add(template.replace("<param>", "Arrangement openingDate"));
            }
        }

        if (!errors.isEmpty())
            checkResult.setStatus(ResponseStatus.BAD);
        return checkResult;
    }
}
