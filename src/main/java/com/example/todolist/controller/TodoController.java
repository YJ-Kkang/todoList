package com.example.todolist.controller;

import com.example.todolist.dto.TodoRequestDto;
import com.example.todolist.dto.TodoResponseDto;
import com.example.todolist.entity.Todo;
import com.example.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
//투두 컨트롤러의 생성자 추가하는 부분(파이널로 선언되어 있는 저 부분을 생성자로 만들겠다는 뜻)
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    // 주입된 의존성을 변경할 수 없어 객체의 상태를 안전하게 유지할 수 있다.
    //             타입         이름
    private final TodoService todoService;

    /**
     * 생성자 주입
     * 클래스가 필요로 하는 의존성을 생성자를 통해 전달하는 방식
     * @param todoService @Service로 등록된 TodoService 구현체인 Impl
     */
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    //투두 생성(값을 돌려받고 싶을 때 post 사용)
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto) {
        Todo todo = todoService.createTodo(
                requestDto.getTask(), requestDto.getName(), requestDto.getPassword()
        );

        return new ResponseEntity<>(new TodoResponseDto(todo), HttpStatus.CREATED);
    }

    //전체 조회
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> findAllTodos(@RequestParam(required = false) String name,
                                                              @RequestParam(required = false) LocalDate updatedOn) {

        List<Todo> todoList = todoService.findAllTodos(name, updatedOn);

        //todoList.stream().map(TodoResponseDto::new).toList() -> 투두리스트를 투투레스폰스디티오로 바꾼 것
        return new ResponseEntity<>(todoList.stream().map(TodoResponseDto::new).toList(), HttpStatus.OK);
    }

    // 단건 조회 (Long id 값에 따라 조회하겠다)
    //조회나 삭제는 바디에 패스워드 보낼 수 없음 -> 쿼리 파람 이용해 전송
    @GetMapping("/{id}")
    //요청 경로변수용 어노테이션
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
        Todo todo = todoService.findTodoById(id);
        return new ResponseEntity<>(new TodoResponseDto(todo), HttpStatus.OK);
    }

    //수정 기능(dto 쓸 때 requestDto처럼 자세히 쓰기)
    @PutMapping("/update/{id}")
    public ResponseEntity<TodoResponseDto> updateTodoById(
            @PathVariable Long id,
            @RequestBody TodoRequestDto requestDto,
            @RequestParam String password
    ) {
        Todo todo = todoService.findTodoById(id);
        if (todo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(todo.getPassword(), password)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Todo updatedtodo = todoService.updateTodoById(id, requestDto, password);

        return new ResponseEntity<>(new TodoResponseDto(updatedtodo), HttpStatus.OK);
    }

    //삭제 기능
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long id,
            @RequestParam String password) {

        //만약 id가 존재하지 않는 경우 not found로 응답
        Todo todo = todoService.findTodoById(id);
        if (todo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //패스워드가 다른 경우
        if (!Objects.equals(todo.getPassword(), password)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}