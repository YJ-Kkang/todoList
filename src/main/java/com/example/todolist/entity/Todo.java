package com.example.todolist.entity;

import com.example.todolist.dto.TodoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Todo {
    //ID 자동 생성
    private Long id;
    // task, name, password 직접 입력해야 함
    private String task;
    private String name;
    private String password;
    //now 메서드 이용(생성, 수정 시 현재 시간 사용)하여 자동으로 데이터 베이스에 저장
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    //생성자(새로운 투두 생성할 때 호출)
    public Todo(Long id, String task, String name, String password) {
        this.id = id;
        this.task = task;
        this.name = name;
        this.password = password;
        //생성 시 현재 시간 설정
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    // 수정 메서드
    public void update(TodoRequestDto requestDto) {
        this.task = requestDto.getTask();
        this.name = requestDto.getName();
        this.password = requestDto.getPassword();
        //업데이트 시 현재 시간 설정
        this.updatedOn = LocalDateTime.now();
    }

    public void updateTask(TodoRequestDto requestDto) {
        this.task = requestDto.getTask();
    }

}