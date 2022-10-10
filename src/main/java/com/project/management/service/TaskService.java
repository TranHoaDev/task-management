package com.project.management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.project.management.entity.Task;
import com.project.management.entity.TaskStatus;

import java.util.List;

public interface TaskService {

    Page<Task> findByTitleContaining(String title, Pageable page);

    List<Task> findAll();

    List<TaskStatus> findAllTasks();
}
