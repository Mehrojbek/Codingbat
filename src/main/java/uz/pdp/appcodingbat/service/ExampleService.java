package uz.pdp.appcodingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcodingbat.entity.Example;
import uz.pdp.appcodingbat.entity.Task;
import uz.pdp.appcodingbat.payload.ApiResponse;
import uz.pdp.appcodingbat.payload.ExampleDto;
import uz.pdp.appcodingbat.repository.ExampleRepository;
import uz.pdp.appcodingbat.repository.TaskRepository;

import java.util.*;

@Service
public class ExampleService {
    @Autowired
    ExampleRepository exampleRepository;
    @Autowired
    TaskRepository taskRepository;


    /**
     * GET ALL EXAMPLE
     * @return EXAMPLE LIST
     */
    public List<Example> getAll(){
        return exampleRepository.findAll();
    }


    /**
     * GET ONE EXAMPLE WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse getOne(Integer id){
        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (!optionalExample.isPresent())
            return new ApiResponse("example not found",false);
        return new ApiResponse("success",true,optionalExample.get());
    }


    /**
     * ADD NEW EXAMPLE WITH EXAMPLE_DTO
     * @param exampleDto
     * @return ApiResponse
     */
    public ApiResponse add(ExampleDto exampleDto){
        //CHECK UNIQUE TASK_ID AND TEXT
        boolean exists = exampleRepository.existsByTextAndTaskId(exampleDto.getText(), exampleDto.getTaskId());
        if (exists){
            //CHECK THIS EXAMPLE IS_ACTIVE
            Example byTextAndTaskId = exampleRepository.findByTextAndTaskId(exampleDto.getText(), exampleDto.getTaskId());
            if (byTextAndTaskId.isActive())
                return new ApiResponse("this example exist",false);
            byTextAndTaskId.setActive(true);
            exampleRepository.save(byTextAndTaskId);
                return new ApiResponse("example added",true);
        }

        //CHECK TASK
        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("task not found",false);
        if (!optionalTask.get().isActice())
            return new ApiResponse("task deleted",false);

        Example example=new Example(exampleDto.getText(), optionalTask.get());
        exampleRepository.save(example);
        return new ApiResponse("example added",true);
    }


    /**
     * DELETE EXAMPLE WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id){
        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (!optionalExample.isPresent())
            return new ApiResponse("example not found",false);
        Example example = optionalExample.get();
        example.setActive(false);
        exampleRepository.save(example);
        return new ApiResponse("example deleted",true);
    }


    /**
     * EDIT EXAMPLE WITH ID EXAMPLE_DTO
     * @param id
     * @param exampleDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, ExampleDto exampleDto){
        //CHECK UNIQUE
        boolean exists = exampleRepository.existsByTextAndTaskIdAndIdNot(exampleDto.getText(), exampleDto.getTaskId(), id);
        if (exists)
            return new ApiResponse("this example exist",false);

        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (!optionalExample.isPresent())
            return new ApiResponse("example not found",false);

        //CHECK TASK
        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("task not found",false);
        if (!optionalTask.get().isActice())
            return new ApiResponse("task deleted",false);

        Example example = optionalExample.get();
        example.setText(exampleDto.getText());
        example.setTask(optionalTask.get());
        return new ApiResponse("example edited",true);
    }





}
