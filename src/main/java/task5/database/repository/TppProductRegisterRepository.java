package task5.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task5.database.model.TppProductRegister;

import java.util.List;

@Repository
public interface TppProductRegisterRepository extends JpaRepository<TppProductRegister, Long> {

//    @Query
//    Integer countByProductIdAndType(Long instanceId, String typeCode);

    @Query(value = "select count(*) from TppProductRegister where productId = ?1 and type.value  = ?2")
    Integer countProductIdAndType(Long instanceId, String typeCode);

    @Query(value = "select reg.id from TppProductRegister reg where reg.productId = ?1")
    List<Long> findAllIdByProductId(Long productId);
}