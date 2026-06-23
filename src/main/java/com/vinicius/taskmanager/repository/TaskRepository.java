package com.vinicius.taskmanager.repository;

import com.vinicius.taskmanager.model.Task;
import com.vinicius.taskmanager.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitleContainingIgnoreCase(String title);

    List<Task> findByStatusAndTitleContainingIgnoreCase(TaskStatus status, String title);
}