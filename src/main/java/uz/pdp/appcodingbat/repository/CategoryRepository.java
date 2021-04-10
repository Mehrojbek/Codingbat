package uz.pdp.appcodingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcodingbat.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByNameAndLanguageIdAndIdNot(String name, Integer language_id, Integer id);
    boolean existsByNameAndLanguageId(String name, Integer language_id);
    long countAllByNameStartingWithAndNameEndingWith(String deleted, String name);
}
