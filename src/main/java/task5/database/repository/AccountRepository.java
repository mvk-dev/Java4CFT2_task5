package task5.database.repository;

import task5.database.model.Account;

public interface AccountRepository {
    Account findAccountByPoolId(Long accountPoolId);
}