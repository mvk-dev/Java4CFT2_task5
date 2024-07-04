package task5.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task5.database.model.Agreement;

import java.util.List;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {
    @Query(value = "select distinct agr.productId from Agreement agr where agr.number = ?1")
    List<String> findDistinctProductIdByNumber(String number);
}
