package task5.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task5.database.model.Account;
import task5.database.model.AccountPool;
import task5.database.repository.AccountPoolRepository;
import task5.database.repository.AccountRepository;
import task5.register.dto.RegisterDto;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountPoolRepository accountPoolRepository;

    public Account getAccount(RegisterDto dto) {
        AccountPool pool = accountPoolRepository.findFirstByDto(dto);
        if (pool == null)
            throw new RuntimeException("Не найден пул счетов для указанных в запросе параметров");

        Account account = accountRepository.findAccountByPoolId(pool.getId());
        if (account == null)
            throw new RuntimeException("Не найден доступный счёт в пуле счетов");

        return account;
    }

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountPoolRepository accountPoolRepository) {
        this.accountRepository = accountRepository;
        this.accountPoolRepository = accountPoolRepository;
    }
}
