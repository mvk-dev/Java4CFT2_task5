package task5.database.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task5.database.model.TppRefProductRegisterType;

import java.util.List;

@Repository
public interface TppRefProductRegisterTypeRepository extends CrudRepository<TppRefProductRegisterType, Long> {
    @Query(value = "select count(*) from TppRefProductRegisterType where value = ?1")
    Integer countRegisterType(String typeCode);

    @Query(value = "select type from TppRefProductRegisterType type where type.value = ?1")
    TppRefProductRegisterType findByRegisterTypeCode(String code);

    @Query(value = "select count(*) " +
            "from TppRefProductRegisterType  " +
            "where productClass.value = ?1 and accountTypeEntity.value = ?2")
    int countByProductCodeAccountType(String productCode, String accountType);

    @Query(value = "select reg_type " +
            "from TppRefProductRegisterType reg_type " +
            "where reg_type.productClass.value = ?1 and reg_type.accountTypeEntity.value = ?2")
    List<TppRefProductRegisterType> findByProductCodeAccountType(String productCode, String accountType);
}
