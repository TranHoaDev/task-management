package com.project.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.project.management.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByTitleContaining(String title, Pageable page);

    @Query(value = "select * from tasks where id = ?", nativeQuery = true)
    Page<Task> findAllTaskByTaskstatusId(Long id, Pageable pageable);



}
