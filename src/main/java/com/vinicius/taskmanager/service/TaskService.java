package com.vinicius.taskmanager.service;

import com.vinicius.taskmanager.model.Task;
import com.vinicius.taskmanager.model.TaskStatus;
import com.vinicius.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada com id: " + id));
    }

    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> search(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    public Task update(Long id, Task taskAtualizada) {
        Task taskExistente = findById(id);
        taskExistente.setTitle(taskAtualizada.getTitle());
        taskExistente.setDescription(taskAtualizada.getDescription());
        taskExistente.setStatus(taskAtualizada.getStatus());
        return taskRepository.save(taskExistente);
    }

    public void delete(Long id) {
        findById(id);
        taskRepository.deleteById(id);
    }
}