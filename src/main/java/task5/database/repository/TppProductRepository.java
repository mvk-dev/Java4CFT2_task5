package task5.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task5.database.model.TppProduct;

import java.util.List;

@Repository
public interface TppProductRepository extends JpaRepository<TppProduct, Long> {
    @Query(value = "select prod.id from TppProduct prod where prod.number = ?1")
    List<String> findByNumber(String number);

    @Query(value = "select count(*) from TppProduct prod where prod.id = ?1")
    long countIdByInstanceId(long instanceId);
}
