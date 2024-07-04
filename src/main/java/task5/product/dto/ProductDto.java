package task5.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductDto {
    private Long instanceId;
    private String productType;
    private String productCode;
    private String registerType;
    private String mdmCode;
    private String contractNumber;
    private LocalDate contractDate;
    private Long priority;
    private BigDecimal interestRatePenalty;
    private BigDecimal minimalBalance;
    private BigDecimal thresholdAmount;
    private String accountingDetails;
    private String rateType;
    private BigDecimal taxPercentageRate;
    private BigDecimal technicalOverdraftLimitAmount;
    private Integer contractId;
    private String branchCode;
    private String isoCurrencyCode;
    private String urgencyCode;
    private Integer referenceCode;

    private List<ProductAddProperty> additionalPropertiesVip;
    private List<ProductArrangementDto> instanceArrangement;
}
