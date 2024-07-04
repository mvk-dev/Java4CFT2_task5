package task5.mappers;

import org.springframework.stereotype.Component;
import task5.database.model.Agreement;
import task5.database.model.TppProduct;
import task5.database.model.TppRefProductRegisterType;
import task5.product.dto.ProductArrangementDto;
import task5.product.dto.ProductDto;
import task5.register.dto.RegisterDto;

@Component
public class MapperDto {
    public TppProduct mapProductDtoToTppProduct(ProductDto dto) {
        return new TppProduct(null,
                null, //dto.getProductCode() ?
                null, // dto.getMdmCode() ?
                dto.getProductType(),
                dto.getContractNumber(),
                dto.getPriority(),
                dto.getContractDate(),
                null,
                null,
                null,
                dto.getInterestRatePenalty(),
                dto.getMinimalBalance(),
                dto.getThresholdAmount(),
                null,
                null,
                dto.getTaxPercentageRate(),
                null,
                null
        );
    }

    public RegisterDto mapProductDtoToRegisterDto(ProductDto dto, TppRefProductRegisterType registerType) {
        return new RegisterDto(dto.getInstanceId(),
                registerType.getValue(),
                null,
                dto.getIsoCurrencyCode(),
                dto.getBranchCode(),
                dto.getUrgencyCode(),
                dto.getMdmCode(),
                null,
                null,
                null,
                null);
    }

    public Agreement mapArrangementDtoToAgreement(ProductArrangementDto dto, TppProduct product) {
        return new Agreement(null,
                product.getId(),
                dto.getGeneralAgreementId(),
                dto.getSupplementaryAgreementId(),
                dto.getArrangementType(),
                dto.getSchedulerJobId(),
                dto.getNumber(),
                dto.getOpeningDate(),
                dto.getClosingDate(),
                dto.getCancelDate(),
                null,
                dto.getCancellationReason(),
                dto.getStatus(),
                dto.getInterestCalculationDate(),
                dto.getInterestRate(),
                dto.getCoefficient(),
                dto.getCoefficientAction(),
                dto.getMinimumInterestRate(),
                dto.getMaximalInterestRateCoefficient(),
                dto.getMinimumInterestRateCoefficientAction(),
                dto.getMaximalInterestRate(),
                dto.getMaximalInterestRateCoefficient(),
                dto.getMaximalInterestRateCoefficientAction(),
                product
        );
    }
}
