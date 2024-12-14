package com.example.todolist.service;

//컨트롤 옵션 O -> 안쓰는 import 제거
import com.example.todolist.dto.TodoRequestDto;
import com.example.todolist.entity.Todo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TodoService {
    private final Map<Long, Todo> todoList = new HashMap<>();

    //투두 생성
    public Todo createTodo(String task, String name, String password) {
        // 필요한 값 : id, task, name, password
        Long todoId = todoList.isEmpty() ? 1 : Collections.max(todoList.keySet()) + 1;
        
        Todo todo = new Todo(todoId, task, name, password);
        
        todoList.put(todoId, todo);

        return todo;
    }

    //전체 조회
    //접근 제어자 / 타입(메소드가 반환해야할 타입) / 이름 (매개변수) {}
    public List<Todo> findAllTodos(String name, LocalDate updatedOn) {
        List<Todo> responseList = new ArrayList<>();

        for (Todo todo : todoList.values()) {
            // 작성자명 필터링
            //and 조건이 참이려면 둘 다 참. -> 널이 아닌 게 참 && 투두에 있는 이름과 사용자가 입력한 이름이 다르면 참(같으면 거짓) -> 컨티뉴
            if ( (name != null) && (!todo.getName().equals(name)) ) {
                //밑에 거가 실행되지 않고 다시 반복한다
                continue;
            }

            // 수정일 필터링 (YYYY-MM-DD 형식 비교)
            //toLocalDate 로컬데이트타임을 로컬데이트로 바꿔주는 것.
            //and 조건이 참이려면 둘 다 참. -> 널이 아닌 게 참 && 투두에 있는 수정날짜가 사용자 입력 날짜와 다르면 참 -> 컨티뉴
            if (updatedOn != null && !todo.getUpdatedOn().toLocalDate().equals(updatedOn)) {
                continue;
            }

            //위에서 컨티뉴가 안되어야 이쪽으로 온다
            responseList.add(todo);
        }

        // 수정일 기준 내림차순 정렬
        //sort: 정렬(responseList에 있는 투두 2개를 가져와서 t1, t2로 선언. -> t1과 t2에 있는 수정날짜를 가져와서 비교.(compareTo)
        responseList.sort((t1, t2) -> t2.getUpdatedOn().compareTo(t1.getUpdatedOn()));

        return responseList;

    }


    // 단건 조회
    public Todo findTodoById(Long id) {
        //get(i)는 인덱스로 쓴다는 것
        //get(키 값) -> 벨류값인 투두가 나옴.
        return todoList.get(id);
    }

    //수정 기능(dto 쓸 때 requestDto처럼 자세히 쓰기)
    public Todo updateTodoById(
            Long id,
            TodoRequestDto requestDto,
            String password
    ) {
        Todo todo = todoList.get(id);

        //투두 수정
        todo.update(requestDto);
        
        return todo;
    }

    //삭제 기능
    public void deleteTodo(Long id) {
        todoList.remove(id);
    }
}