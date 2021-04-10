package uz.pdp.appcodingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcodingbat.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
}
