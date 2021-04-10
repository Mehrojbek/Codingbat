package uz.pdp.appcodingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcodingbat.entity.Example;

public interface ExampleRepository extends JpaRepository<Example,Integer> {
    boolean existsByTextAndTaskIdAndIdNot(String text, Integer task_id, Integer id);
    boolean existsByTextAndTaskId(String text, Integer task_id);
    Example findByTextAndTaskId(String text, Integer task_id);
}
