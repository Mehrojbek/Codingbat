package uz.pdp.appcodingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcodingbat.entity.Answer;
import uz.pdp.appcodingbat.entity.Task;
import uz.pdp.appcodingbat.entity.User;
import uz.pdp.appcodingbat.payload.AnswerDto;
import uz.pdp.appcodingbat.payload.ApiResponse;
import uz.pdp.appcodingbat.repository.AnswerRepository;
import uz.pdp.appcodingbat.repository.TaskRepository;
import uz.pdp.appcodingbat.repository.UserRepository;

import java.util.*;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;


    /**
     * GET ALL ANSWER
     * @return ANSWER LIST
     */
    public List<Answer> getAll(){
        return answerRepository.findAll();
    }


    /**
     * GET ONE ANSWER WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse getOne(Integer id){
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (!optionalAnswer.isPresent())
            return new ApiResponse("answer not found",false);
        return new ApiResponse("success",true,optionalAnswer.get());
    }


    /**
     * ADD NEW ANSWER WITH ANSWER_DTO
     * @param answerDto
     * @return ApiResponse
     */
    public ApiResponse add(AnswerDto answerDto){

        if (answerDto.getUserId()!=null) {
            //CHECK USER
            Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
            if (!optionalUser.isPresent())
                return new ApiResponse("user not found", false);
            if (!optionalUser.get().isActive())
                return new ApiResponse("user deleted", false);


            //CHECK TASK
            Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
            if (!optionalTask.isPresent())
                return new ApiResponse("task not found",false);
            if (!optionalTask.get().isActice())
                return new ApiResponse("task deleted",false);

            Answer answer=new Answer(answerDto.getText(), optionalUser.get(), optionalTask.get());
            boolean isCorrect = checkAnswer(answerDto.getText());
            answer.setCorrect(isCorrect);
            answerRepository.save(answer);
            return new ApiResponse(isCorrect?"All correct":"Answer is incorrect",true,answer);
        }
        if (checkAnswer(answerDto.getText()))
            return new ApiResponse("All correct",true);
        return new ApiResponse("Answer is incorrect",false);
    }


    /**
     * DELETE ANSWER WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id){
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (!optionalAnswer.isPresent())
            return new ApiResponse("answer not found",false);
        Answer answer = optionalAnswer.get();
        answer.setActive(false);
        answerRepository.save(answer);
        return new ApiResponse("answer deleted",true);
    }


    /**
     * EDIT ANSWER WITH ID AND ANSWER_DTO
     * @param id
     * @param answerDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, AnswerDto answerDto){
        //CHECK ANSWER
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (!optionalAnswer.isPresent())
            return new ApiResponse("answer noy found",false);
        Answer answer = optionalAnswer.get();

        //CHECK TASK
        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("task not found",false);
        if (!optionalTask.get().isActice())
            return new ApiResponse("task deleted",false);

        //CHECK USER
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("user not found",false);
        if (!optionalUser.get().isActive())
            return new ApiResponse("user deleted",false);

        answer.setTask(optionalTask.get());
        answer.setUser(optionalUser.get());
        answer.setText(answerDto.getText());
        answer.setCorrect(checkAnswer(answerDto.getText()));

        answerRepository.save(answer);
        return new ApiResponse("answer edited",true);
    }



    //THIS METHOD ANSWER CHECKER
    public boolean checkAnswer(String answer){
        return true;
    }
}
