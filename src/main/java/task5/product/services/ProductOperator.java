package task5.product.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import task5.database.model.Agreement;
import task5.database.model.TppProduct;
import task5.database.model.TppRefProductRegisterType;
import task5.database.repository.AgreementRepository;
import task5.database.repository.TppProductRegisterRepository;
import task5.database.repository.TppProductRepository;
import task5.database.repository.TppRefProductRegisterTypeRepository;
import task5.mappers.MapperDto;
import task5.product.dto.ProductArrangementDto;
import task5.product.dto.ProductDto;
import task5.product.dto.ResponseProductDto;
import task5.register.dto.RegisterDto;
import task5.register.services.RegisterOperator;
import task5.utils.CheckResult;
import task5.utils.Checkable;
import task5.utils.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductOperator {
    private static final String C_CL_ACC_TYPE = "Клиентский";
    private MapperDto mapper;
    private final TppProductRepository productRepository;
    private final AgreementRepository agreementRepository;
    private final TppProductRegisterRepository registerRepository;
    private final TppRefProductRegisterTypeRepository registerTypeRepository;
    private final List<Checkable<ProductDto, CheckResult>> checksNewProduct;
    private final List<Checkable<ProductDto, CheckResult>> checksExistProduct;
    private final Checkable<ProductDto, CheckResult> bodyResponseCheck;

    private RegisterOperator registerOperator;

    @Autowired
    public void setRegisterOperator(RegisterOperator registerOperator) {
        this.registerOperator = registerOperator;
    }

    @Autowired
    public void setMapper(MapperDto mapper) {
        this.mapper = mapper;
    }

    public CheckResult validate(ProductDto dto) {
        if (dto == null)
            return new CheckResult(ResponseStatus.BAD, List.of("Request body is empty"));

        CheckResult checkResult = bodyResponseCheck.apply(dto);
        if (checkResult.getStatus() != ResponseStatus.OK)
            return checkResult;

        if (dto.getInstanceId() == null) {
            for (Checkable<ProductDto, CheckResult> check : checksNewProduct) {
                checkResult = check.apply(dto);
                if (checkResult.getStatus() != ResponseStatus.OK)
                    return checkResult;
            }
        } else {
            for (Checkable<ProductDto, CheckResult> check : checksExistProduct) {
                checkResult = check.apply(dto);
                if (checkResult.getStatus() != ResponseStatus.OK)
                    return checkResult;
            }
        }

        return new CheckResult(ResponseStatus.OK, null);
    }

    @Transactional
    public ResponseProductDto process(ProductDto dto) {
        TppProduct product;
        if (dto.getInstanceId() == null) {
            product = createProduct(dto);
            dto.setInstanceId(product.getId());
            createProductRegisters(dto);
        } else
            product = productRepository.findById(dto.getInstanceId()).orElseThrow();

        List<Long> agreementIdList = new ArrayList<>();
        for (ProductArrangementDto arrangementDto : dto.getInstanceArrangement()) {
            Agreement agreement = mapper.mapArrangementDtoToAgreement(arrangementDto, product);
            agreementRepository.save(agreement);
            agreementIdList.add(agreement.getId());
        }

        List<Long> registerIdList = registerRepository.findAllIdByProductId(product.getId());

        return new ResponseProductDto(product.getId(), registerIdList, agreementIdList);
    }

    private void createProductRegisters(ProductDto dto) {
        List<TppRefProductRegisterType> registerTypes = registerTypeRepository.findByProductCodeAccountType(dto.getProductCode(), C_CL_ACC_TYPE);
        registerTypes.forEach(type -> {
            RegisterDto registerDto = mapper.mapProductDtoToRegisterDto(dto, type);
            registerOperator.createProductRegister(registerDto);
        });
    }

    private TppProduct createProduct(ProductDto dto) {
        TppProduct product = mapper.mapProductDtoToTppProduct(dto);
        return productRepository.save(product);
    }

    @Autowired
    public ProductOperator(@Qualifier("getCheckProductRequestBody") Checkable<ProductDto, CheckResult> bodyResponseCheck,
                           @Qualifier("getChecksNewProduct") List<Checkable<ProductDto, CheckResult>> checksNewProduct,
                           @Qualifier("getChecksExistProduct") List<Checkable<ProductDto, CheckResult>> checksExistProduct,
                           TppProductRepository productRepository,
                           AgreementRepository agreementRepository,
                           TppProductRegisterRepository registerRepository,
                           TppRefProductRegisterTypeRepository registerTypeRepository
    ) {
        this.bodyResponseCheck = bodyResponseCheck;
        this.checksNewProduct = checksNewProduct;
        this.checksExistProduct = checksExistProduct;
        this.productRepository = productRepository;
        this.agreementRepository = agreementRepository;
        this.registerRepository = registerRepository;
        this.registerTypeRepository = registerTypeRepository;
    }
}
