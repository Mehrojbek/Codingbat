package uz.pdp.appcodingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcodingbat.entity.Category;
import uz.pdp.appcodingbat.payload.Deleted;
import uz.pdp.appcodingbat.entity.Task;
import uz.pdp.appcodingbat.payload.ApiResponse;
import uz.pdp.appcodingbat.payload.TaskDto;
import uz.pdp.appcodingbat.repository.CategoryRepository;
import uz.pdp.appcodingbat.repository.TaskRepository;

import java.util.*;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    CategoryRepository categoryRepository;


    /**
     * GET ALL TASKS
     * @return TASK LIST
     */
    public List<Task> getAll(){
        return taskRepository.findAll();
    }


    /**
     * GET ONE TASK WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse getOne(Integer id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not found",false);
        return new ApiResponse("Succuss",true,optionalTask.get());
    }


    /**
     * ADD TASK WITH TASK_DTO
     * @param taskDto
     * @return ApiResponse
     */
    public ApiResponse add(TaskDto taskDto){
        //CHECK UNIQUE CATEGORY_ID AND NAME
        boolean exists = taskRepository.existsByNameAndCategoryId(taskDto.getName(), taskDto.getCategoryId());
        if (exists)
            return new ApiResponse("this task already exist",false);

        //CHECK CATEGORY
        Optional<Category> optionalCategory = categoryRepository.findById(taskDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new ApiResponse("category not found",false);
        if (!optionalCategory.get().isActive())
            return new ApiResponse("category deleted",false);

        Task task=new Task();
        task.setName(taskDto.getName());
        task.setText(taskDto.getText());
        task.setMethod(taskDto.getMethod());
        task.setCategory(optionalCategory.get());

        if (taskDto.getSolution()!=null){
            task.setSolution(taskDto.getSolution());
        }
        if (taskDto.getHint()!=null){
            task.setHint(taskDto.getHint());
        }
        if (taskDto.isHasStar()){
            task.setHasStar(taskDto.isHasStar());
        }

        taskRepository.save(task);
        return new ApiResponse("task added",true);
    }


    /**
     * DELETE TASK WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("task not found",false);
        Task task = optionalTask.get();

        //HOW MANY DELETED
        long numberOfDeletedTask = taskRepository.countByNameStartingWithAndNameEndingWith(Deleted.DELETED, task.getName())+1;
        task.setName(Deleted.DELETED+numberOfDeletedTask+":"+task.getName());
        task.setActice(false);
        taskRepository.save(task);
        return new ApiResponse("task deleted",true);
    }


    /**
     * EDIT TASK WITH ID AND TASK_DTO
     * @param id
     * @param taskDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, TaskDto taskDto){
        //CHECK UNIQUE CATEGORY_ID AND NAME
        boolean exists = taskRepository.existsByNameAndCategoryIdAndIdNot(taskDto.getName(), taskDto.getCategoryId(), id);
        if (exists)
            return new ApiResponse("this task exist",false);

        //CHECK TASK
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("task not found",false);
        Task task = optionalTask.get();

        //CHECK CATEGORY
        Optional<Category> optionalCategory = categoryRepository.findById(taskDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new ApiResponse("category not found",false);
        if (!optionalCategory.get().isActive())
            return new ApiResponse("category deleted",false);

        task.setName(taskDto.getName());
        task.setText(taskDto.getText());
        task.setMethod(taskDto.getMethod());
        task.setCategory(optionalCategory.get());

        if (taskDto.getSolution()!=null){
            task.setSolution(taskDto.getSolution());
        }
        if (taskDto.getHint()!=null){
            task.setHint(taskDto.getHint());
        }
        if (taskDto.isHasStar()){
            task.setHasStar(taskDto.isHasStar());
        }

        taskRepository.save(task);
        return new ApiResponse("task edited",true);
    }
}
