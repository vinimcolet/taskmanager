package com.vinicius.taskmanager;

import com.vinicius.taskmanager.exception.ResourceNotFoundException;
import com.vinicius.taskmanager.model.Task;
import com.vinicius.taskmanager.model.TaskStatus;
import com.vinicius.taskmanager.repository.TaskRepository;
import com.vinicius.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Learn testing");
        task.setDescription("JUnit and Mockito");
        task.setStatus(TaskStatus.PENDING);
    }

    @Test
    void createTask_shouldReturnSavedTask() {
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.create(task);

        assertNotNull(result);
        assertEquals("Learn testing", result.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void findAll_shouldReturnTaskList() {
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<Task> result = taskService.findAll();

        assertEquals(1, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnTask_whenExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.findById(99L));
    }

    @Test
    void update_shouldReturnUpdatedTask_whenExists() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated title");
        updatedTask.setDescription("New description");
        updatedTask.setStatus(TaskStatus.DONE);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.update(1L, updatedTask);

        assertNotNull(result);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void delete_shouldRemoveTask_whenExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById(1L);

        assertDoesNotThrow(() -> taskService.delete(1L));
        verify(taskRepository, times(1)).deleteById(1L);
    }
}