package task5.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task5.database.model.TppRefAccountType;

@Repository
public interface TppRefAccountTypeRepository extends CrudRepository<TppRefAccountType, Long> {
}