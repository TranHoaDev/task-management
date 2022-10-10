package com.project.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.management.entity.TaskStatus;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus,Long> {

}
