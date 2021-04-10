package uz.pdp.appcodingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcodingbat.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    boolean existsByNameAndCategoryId(String name, Integer category_id);
    long countByNameStartingWithAndNameEndingWith(String deleted, String name);
    boolean existsByNameAndCategoryIdAndIdNot(String name, Integer category_id, Integer id);
}
