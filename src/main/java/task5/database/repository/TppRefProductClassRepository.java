package task5.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task5.database.model.TppRefProductClass;

@Repository
public interface TppRefProductClassRepository extends JpaRepository<TppRefProductClass, Long> {
}