package com.example.todolist.entity;

import com.example.todolist.dto.TodoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Todo {

    private Long id;
    private String task;
    private String name;
    private String password;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    // 수정
    public void update(TodoRequestDto requestDto) {
        this.task = requestDto.getTask();
        this.name = requestDto.getName();
        this.updatedOn = requestDto.getUpdatedOn();
    }

    public void updateTask(TodoRequestDto requestDto) {
        this.task = requestDto.getTask();
    }

}