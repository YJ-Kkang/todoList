package com.example.todolist.controller;

import com.example.todolist.dto.TodoRequestDto;
import com.example.todolist.dto.TodoResponseDto;
import com.example.todolist.entity.Todo;
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
    //투두 값이 응답되는 것(Dto에 저장되어 있음)              (            요청 이름 : 매개변수(값을 넣었을 때 여기에서 해줘라))
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
        Todo todo = new Todo(todoId, requestDto.getTask(), requestDto.getName(), requestDto.getPassword());
        /*
        Inmemory DB에 투두 저장(자바의 맵절 구조를 사용하여 그 안에 저장)
        키값은 todoId, 저장될 객체 형태는 투두
        */
        todoList.put(todoId, todo);
        return new ResponseEntity<>(new TodoResponseDto(todo), HttpStatus.CREATED);
    }

    //전체 조회
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> findAllTodos(@RequestParam(required = false) String name, @RequestParam(required = false) String updatedOn) {
        List<TodoResponseDto> responseList = new ArrayList<>();

        for (Todo todo : todoList.values()) {
            // 작성자명 필터링
            if (name != null && !todo.getName().equals(name)) {
                continue;
            }

            // 수정일 필터링 (YYYY-MM-DD 형식 비교)
            if (updatedOn != null && !todo.getUpdatedOn().toLocalDate().toString().equals(updatedOn)) {
                continue;
            }

            responseList.add(new TodoResponseDto(todo));
        }

        // 수정일 기준 내림차순 정렬
        responseList.sort((t1, t2) -> t2.getUpdatedOn().compareTo(t1.getUpdatedOn()));

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }


    // 단건 조회 (Long id 값에 따라 조회하겠다)
    //조회나 삭제는 바디에 패스워드 보낼 수 없음 -> 쿼리 파람 이용해 전송
    @GetMapping("/{id}")
    //요청 경로변수용 어노테이션
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
        //get(i)는 인덱스로 쓴다는 것
        Todo todo = todoList.get(id);
        if (todo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new TodoResponseDto(todo), HttpStatus.OK);
    }

    //수정 기능(dto 쓸 때 requestDto처럼 자세히 쓰기)
    @PutMapping("/update/{id}")
    public ResponseEntity<TodoResponseDto> updateTodoById(
            @PathVariable Long id,
            @RequestBody TodoRequestDto requestDto,
            @RequestParam String password
    ) {
        Todo todo = todoList.get(id);
        // 필수값 검증
        if (todo == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!todo.getPassword().equals(password)) {
            //비번 일치하지 않으면 접근 거부
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        //투두 수정
        todo.update(requestDto);
        //응답
        return new ResponseEntity<>(new TodoResponseDto(todo), HttpStatus.OK);
    }

    //삭제 기능
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id, @RequestParam String password) {
        Todo todo = todoList.get(id);

        // todoList의 Key값에 id를 포함하고 있는 경우
        if (todoList.containsKey(id)) {
            if (todo == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if(!todo.getPassword().equals(password)) {
                //비번 일치하지 않으면 접근 거부
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            // key가 id인 value 삭제
            todoList.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        // 포함하고 있지 않은 경우
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}