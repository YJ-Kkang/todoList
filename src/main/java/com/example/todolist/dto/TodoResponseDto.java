package com.example.todolist.dto;

import com.example.todolist.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {

    private Long id;
    private String task;
    private String name;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    //투두 class를 인자로 가지는 생성자
    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.task = todo.getTask();
        this.name = todo.getName();
        this.createdOn = todo.getCreatedOn();
        this.updatedOn = todo.getUpdatedOn();
    }
}