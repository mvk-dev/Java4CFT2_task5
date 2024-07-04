package task5.database.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import task5.database.model.Account;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account findAccountByPoolId(Long accountPoolId) {
        return entityManager.createQuery("select acc from Account acc " +
                        "where acc.accountPool.id = :accountPoolId", Account.class)
                .setParameter("accountPoolId", accountPoolId)
                .setMaxResults(1)
                .getSingleResult();
    }
}
