package uz.pdp.appcodingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcodingbat.entity.Language;

public interface LanguageRepository extends JpaRepository<Language,Integer> {
    boolean existsByName(String name);
    Language findByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
