package uz.pdp.appcodingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcodingbat.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {

}
