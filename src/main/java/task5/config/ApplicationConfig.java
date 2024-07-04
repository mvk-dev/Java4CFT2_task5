package task5.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import task5.product.dto.ProductDto;
import task5.product.services.*;
import task5.register.dto.RegisterDto;
import task5.register.services.CheckRegisterRequestBody;
import task5.register.services.CheckRegistryTypeCode;
import task5.register.services.CheckTppProductRegistry;
import task5.utils.CheckResult;
import task5.utils.Checkable;

import java.util.List;

@Configuration
public class ApplicationConfig {
    @Bean
    @Qualifier("getChecksForNewRegister")
    List<Checkable<RegisterDto, CheckResult>> getChecksForNewRegister(CheckRegisterRequestBody checkRegisterRequestBody,
                                                                      CheckRegistryTypeCode checkRegistryTypeCode,
                                                                      CheckTppProductRegistry checkTppProductRegistry
    ) {
        return List.of(checkRegisterRequestBody, checkRegistryTypeCode, checkTppProductRegistry);
    }

    @Bean
    Checkable<ProductDto, CheckResult> getCheckProductRequestBody() {
        return new CheckProductRequestBody();
    }

    @Bean
    @Qualifier("getChecksNewProduct")
    List<Checkable<ProductDto, CheckResult>> getChecksNewProduct(CheckProductContractNumber checkProductContractNumber,
                                                                 CheckArrangementNumber checkArrangementNumber,
                                                                 CheckProductClass checkProductClass
    ) {
        return List.of(checkProductContractNumber, checkArrangementNumber, checkProductClass);
    }

    @Bean
    @Qualifier("getChecksExistProduct")
    List<Checkable<ProductDto, CheckResult>> getChecksExistProduct(CheckProductId checkProductId,
                                                                   CheckArrangementNumber checkArrangementNumber
    ) {
        return List.of(checkProductId, checkArrangementNumber);
    }
}
