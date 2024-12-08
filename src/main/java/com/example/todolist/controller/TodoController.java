package com.example.todolist.controller;

import com.example.todolist.dto.TodoRequestDto;
import com.example.todolist.dto.TodoResponseDto;
import com.example.todolist.entity.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private final Map<Long, Todo> todoList = new HashMap<>();
    //메모 생성(값을 돌려받고 싶을 때 post 사용)
    @PostMapping
    //투두 값이 응답되는 것(Dto에 저장되어 있음) ( 요청 이름 : 매개변수(값을 넣었을 때 여기에서 해줘라))
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto) {
        /*
        식별자가 1씩 증가하도록
        Collections.max는 이 안에 있는 것 중 최대값 뽑아냄
        todoList.keySet()은 todoList 안에 있는 key 값들을 다 꺼내보는 것
        key값을 Long으로 했음 -> 모든 Long값을 꺼내서 그 최대값을 반환해주는 것
        isEmpty는 비어있니?라는 뜻
        삼항연산자 == 조건문 ? 맞는 거 : 틀린 거
        */
        Long todoId = todoList.isEmpty() ? 1 : Collections.max(todoList.keySet()) + 1;
        //요청받은 데이터로 투두 객체 생성
        Todo todo = new Todo(todoId, requestDto.getTask(), requestDto.getName(), requestDto.getPassword(), requestDto.getCreatedOn(), requestDto.getUpdatedOn());
        /*
        Inmemory DB에 투두 저장(자바의 맵절 구조를 사용하여 그 안에 저장)
        키값은 todoId, 저장될 객체 형태는 투두
        */
        todoList.put(todoId, todo);
        return new ResponseEntity<>(new TodoResponseDto(todo), HttpStatus.CREATED);
    }

    //전체 조회
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> findAllMemos() {
        List<TodoResponseDto> responseList = new ArrayList<>();
        for (Todo todo : todoList.values()) {
            TodoResponseDto responseDto = new TodoResponseDto(todo);
            responseList.add(responseDto);
        }
        
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    // 단건 조회 (Long id 값에 따라 조회하겠다)
    @GetMapping("/{id}")
    //요청 경로변수용 어노테이션
    public ResponseEntity<TodoResponseDto> findMemoById(@PathVariable Long id) {
        //get(i)는 인덱스로 쓴다는 것
        Todo todo = todoList.get(id);
        if (todo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new TodoResponseDto(todo), HttpStatus.OK);
    }

    //수정 기능(dto 쓸 때 requestDto처럼 자세히 쓰기)
    @PutMapping("/{id}")
    public TodoResponseDto updateTodoById(
            @PathVariable Long id,
            @RequestBody TodoRequestDto requestDto
    ) {
        Todo todo = todoList.get(id);
        todo.update(requestDto);
        return new TodoResponseDto(todo);
    }

    //삭제 기능
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoList.remove(id);
    }
}


@PutMapping("/{id}")
public ResponseEntity<TodoResponseDto> updateTodo(
        @PathVariable Long id,
        @RequestBody TodoRequestDto requestDto
) {

    Todo todo = todoList.get(id);

    // NPE 방지
    if (todo == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 필수값 검증
    if (requestDto.getTask() == null || requestDto.getName() == null || requestDto.getPassword() == null) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // todo 수정
    todo.update(requestDto);

    // 응답
    return new ResponseEntity<>(new TodoResponseDto(todo), HttpStatus.OK);
}