package uz.pdp.appcodingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcodingbat.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;
}
