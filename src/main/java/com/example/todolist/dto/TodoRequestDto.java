package com.example.todolist.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {
    private String task;
    private String name;
    private String password;

    public TodoRequestDto() {}
}