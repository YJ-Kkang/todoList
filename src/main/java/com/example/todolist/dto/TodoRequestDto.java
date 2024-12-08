package com.example.todolist.dto;

import com.example.todolist.entity.Todo;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoRequestDto {
    private Long id;
    private String task;
    private String name;
    private String password;
    private LocalDate createdOn;
    private LocalDate updatedOn;

    //기본 생성자
    public TodoRequestDto() {
        this.createdOn = LocalDate.now();
        this.updatedOn = LocalDate.now();
    }

    //memo 객체가 RequestDto 형태로 바껴서 응답이 되어야 함
    public TodoRequestDto(Todo todo) {
        this.id = todo.getId();
        this.task = todo.getTask();
        this.name = todo.getName();
        this.password = todo.getPassword();
        this.createdOn = todo.getCreatedOn();
        this.updatedOn = todo.getUpdatedOn();
    }
}