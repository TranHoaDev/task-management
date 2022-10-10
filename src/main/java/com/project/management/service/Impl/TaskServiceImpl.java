package com.project.management.service.Impl;

import com.project.management.repository.TaskRepository;
import com.project.management.repository.TaskStatusRepository;
import com.project.management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.management.entity.Task;
import com.project.management.entity.TaskStatus;
import com.project.management.mapper.TaskMapper;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskMapper taskMapper;

    public Page<Task> findByTitleContaining(String title, Pageable page) {
        return taskRepository.findByTitleContaining(title, page);
    }

    @Autowired
    public List<TaskStatus> findAllTasks(){
        return  taskStatusRepository.findAll();
    }

    @Autowired
    public List<Task> findAll() {
        return taskMapper.findAll();
    }
}
