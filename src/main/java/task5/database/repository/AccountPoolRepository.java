package task5.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import task5.database.model.AccountPool;
import task5.register.dto.RegisterDto;

@Repository
public interface AccountPoolRepository extends JpaRepository<AccountPool, Long> {
    @Query(value = "select acc.* from account_pool acc " +
            "join tpp_ref_product_register_type reg_type " +
            "   on acc.registry_type_id = reg_type.id " +
            "where acc.branch_code = :#{#dto.branchCode} " +
            "and acc.currency_code = :#{#dto.currencyCode} " +
            "and acc.mdm_code = :#{#dto.mdmCode} " +
            "and acc.priority_code = :#{#dto.priorityCode} " +
            "and reg_type.value = :#{#dto.registryTypeCode} " +
            " limit 1"
            , nativeQuery = true)
    AccountPool findFirstByDto(@Param("dto") RegisterDto dto);
}