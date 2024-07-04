package task5.register.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import task5.database.model.Account;
import task5.database.model.ProductRegisterState;
import task5.database.model.TppProductRegister;
import task5.database.model.TppRefProductRegisterType;
import task5.database.repository.TppProductRegisterRepository;
import task5.database.repository.TppRefProductRegisterTypeRepository;
import task5.register.dto.RegisterDto;
import task5.register.dto.ResponseRegisterDto;
import task5.utils.AccountService;
import task5.utils.CheckResult;
import task5.utils.Checkable;
import task5.utils.ResponseStatus;

import java.util.List;

@Service
public class RegisterOperator {
    private List<Checkable<RegisterDto, CheckResult>> bodyChecks;
    private TppProductRegisterRepository productRegisterRepository;
    private TppRefProductRegisterTypeRepository registerTypeRepository;
    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    @Qualifier("getChecksForNewRegister")
    public void setBodyChecks(List<Checkable<RegisterDto, CheckResult>> bodyChecks) {
        this.bodyChecks = bodyChecks;
    }

    @Autowired
    public void setProductRegisterRepository(TppProductRegisterRepository productRegisterRepository) {
        this.productRegisterRepository = productRegisterRepository;
    }

    @Autowired
    public void setRegisterTypeRepository(TppRefProductRegisterTypeRepository registerTypeRepository) {
        this.registerTypeRepository = registerTypeRepository;
    }

    public CheckResult validate(RegisterDto dto) {
        if (dto == null)
            return new CheckResult(ResponseStatus.BAD, List.of("Request body is empty"));

        for (Checkable<RegisterDto, CheckResult> check : bodyChecks) {
            CheckResult checkResult = check.apply(dto);
            if (checkResult.getStatus() != ResponseStatus.OK)
                return checkResult;
        }

        return new CheckResult(ResponseStatus.OK, List.of());
    }

    public ResponseRegisterDto process(RegisterDto dto) {
        TppProductRegister register = createProductRegister(dto);
        return new ResponseRegisterDto(register.getId());
    }

    public TppProductRegister createProductRegister(RegisterDto dto) {
        Account account = accountService.getAccount(dto);
        TppRefProductRegisterType registerType = registerTypeRepository.findByRegisterTypeCode(dto.getRegistryTypeCode());

        TppProductRegister register = new TppProductRegister(null,
                dto.getInstanceId(),
                registerType,
                account.getId(),
                dto.getCurrencyCode(),
                ProductRegisterState.OPEN.name(),
                account.getAccountNumber()
        );

        try {
            register = productRegisterRepository.save(register);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сохранении нового продуктового регистра: " + e.getMessage());
        }

        return register;
    }
}
