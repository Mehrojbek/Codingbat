package uz.pdp.appcodingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcodingbat.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {

}