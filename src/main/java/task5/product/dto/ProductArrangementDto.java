package task5.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ProductArrangementDto {
    private String generalAgreementId;
    private String supplementaryAgreementId;
    private String arrangementType;
    private Long schedulerJobId;
    private String number;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private LocalDate cancelDate;
    private Integer validityDuration;
    private String cancellationReason;
    private String status;
    private LocalDate interestCalculationDate;
    private BigDecimal interestRate;
    private BigDecimal coefficient;
    private String coefficientAction;
    private BigDecimal minimumInterestRate;
    private String minimumInterestRateCoefficient;
    private String minimumInterestRateCoefficientAction;
    private BigDecimal maximalInterestRate;
    private BigDecimal maximalInterestRateCoefficient;
    private String maximalInterestRateCoefficientAction;
}
