package com.vinicius.taskmanager.dto;

import com.vinicius.taskmanager.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequest {

    @NotBlank(message = "Título é obrigatório")
    private String title;

    private String description;

    private TaskStatus status;
}