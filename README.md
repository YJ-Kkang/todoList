# Todo List Project
스파르타 내일배움캠프 Spring 4기 과제에 사용한 코드입니다. Java를 이용하여 일정 관리 애플리케이션을 구현하였습니다.

<br>

## 🧑‍🏫 프로젝트 소개
- 이 프로젝트는 간단한 일정 관리 애플리케이션입니다.
- 사용자는 일정 항목을 생성, 조회, 수정, 삭제할 수 있습니다.

<br>

## 🕰️ 개발 기간

\- **2024. 11. 28. (목) ~ 2024. 12. 10. (화)**

\- 공부 기간: 2024. 11. 28. (목) ~ 2024. 11. 05. (수)

\- 개발 기간: 2024. 12. 06. (금) ~ 2024. 12. 10. (화)

<br>

## 🐣 개발자 소개

- 강유진

<img src="https://raw.githubusercontent.com/YJ-Kkang/spa-homework/refs/heads/main/images/yujin.webp" alt="title" width="300"/>

[Git 링크](https://github.com/YJ-Kkang)

[블로그 링크](https://velog.io/@yjkang/posts)


<br>

## 💬 요구 사항 정의
1. **일정 관리 기능**
    - 일정 생성: 사용자가 새로운 일정을 등록할 수 있음.
    - 일정 조회: 등록된 일정을 조회할 수 있음.
    - 일정 수정: 일정의 세부 정보를 수정할 수 있음.
    - 일정 삭제: 등록된 일정을 삭제할 수 있음.

2. **사용자 인증 방식**: 일정 수정 및 삭제 시 비밀번호 확인.

3. **예외 처리**
    - 잘못된 입력 처리: 유효하지 않은 데이터 입력 시 오류 메시지 출력.
    - 비밀번호 불일치 처리: 일정 수정 및 삭제 시 입력한 비밀번호가 일치하지 않을 경우 접근 거부 처리.

4. **3 Layer Architecture**
    - Controller Layer
    - Service Layer
    - Repository Layer

5. **기능 목록**
    - 일정 작성, 수정, 조회 시 반환 받은 일정 정보에 `비밀번호`는 제외.
    - 일정 수정, 삭제 시 선택한 일정의 `비밀번호`와 요청할 때 함께 보낸 `비밀번호`가 일치할 경우에만 가능.
    - 비밀번호가 일치하지 않을 경우 적절한 오류 코드 및 메세지를 반환.
   
<br>

## 📝 설계
1. **클래스 다이어그램**
- `TodoListApplication`: 시작 지점
- `Todo`: 일정 항목을 관리하는 클래스 (ID, Task, Name, Password, CreatedOn, UpdatedOn 포함)
- `TodoRequestDto`: 일정 생성 및 수정 요청 시 사용되는 DTO 클래스
- `TodoResponseDto`: 일정 조회 시 사용되는 DTO 클래스
- `TodoController`: 일정 생성, 조회, 수정, 삭제 기능을 제공하는 컨트롤러 클래스

2. **기능 분해**: 주요 기능을 메소드로 분리.
- `createTodo()`: 새로운 일정 생성
- `findAllTodos()`: 모든 일정 조회
- `findTodoById()`: 특정 일정 조회
- `updateTodoById()`: 일정 수정
- `deleteTodo()`: 일정 삭제

3. **데이터 흐름 설계**: 데이터가 클래스 간에 어떻게 흐를지를 결정.
- 입력: 사용자가 일정 데이터를 입력.
- 처리: 입력된 데이터를 기반으로 일정 생성, 조회, 수정, 삭제.
- 출력: 일정 목록 또는 상세 정보 출력.


<br>

## 🛠 설치 방법
1. Java Development Kit (JDK)를 설치합니다. → [Window](https://teamsparta.notion.site/Window-JDK-f646c4cfdbd34daf81b4315f7abeba1d)    / [Mac](https://teamsparta.notion.site/Mac-JDK-cd42768710404e50a742ce0e187975bf)
2. 이 레포지토리를 클론합니다.
```
bash git clone https://github.com/YJ-Kkang/todolist.git .
```
3. 프로그램을 실행합니다.

<br>

## 🔍 사용 방법
1. [Postman](https://www.postman.com/downloads/)을 사용하여 서버에 요청을 보냅니다.
2. 아래의 `API 명세서`를 참고하여 일정을 생성, 조회, 수정, 삭제합니다.

### 📌 API 명세서


<br>

## 📚 사용 예시
### Lv 1

![title](https://github.com/YJ-Kkang/spaKiosk/blob/dev/images/lv_1_ex_1.png?raw=true)
![title](https://github.com/YJ-Kkang/spaKiosk/blob/dev/images/lv_1_ex_2.png?raw=true)

<br>

### Lv 2

![title](https://github.com/YJ-Kkang/spaKiosk/blob/dev/images/lv_2_ex_1.png?raw=true)
![title](https://github.com/YJ-Kkang/spaKiosk/blob/dev/images/lv_2_ex_2.png?raw=true)

<br>

### Lv 3

![title](https://github.com/YJ-Kkang/spaKiosk/blob/dev/images/lv_3_ex_1.png?raw=true)
![title](https://github.com/YJ-Kkang/spaKiosk/blob/dev/images/lv_3_ex_2.png?raw=true)

<br>

### Lv 4

![title](https://raw.githubusercontent.com/YJ-Kkang/spaKiosk/b8de93c333e65e8d4febb607fc087b3e26323c25/images/lv_4_ex_1.png)
![title](https://raw.githubusercontent.com/YJ-Kkang/spaKiosk/b8de93c333e65e8d4febb607fc087b3e26323c25/images/lv_4_ex_2.png)
![title](https://raw.githubusercontent.com/YJ-Kkang/spaKiosk/b8de93c333e65e8d4febb607fc087b3e26323c25/images/lv_4_ex_3.png)

<br>

### Lv 5

![title](https://raw.githubusercontent.com/YJ-Kkang/spaKiosk/77d8c8a27a4baa11d20c652c7b7d7d6b5a6cb3d9/images/lv_5_ex_1.png)
![title](https://raw.githubusercontent.com/YJ-Kkang/spaKiosk/77d8c8a27a4baa11d20c652c7b7d7d6b5a6cb3d9/images/lv_5_ex_2.png)
![title](https://raw.githubusercontent.com/YJ-Kkang/spaKiosk/77d8c8a27a4baa11d20c652c7b7d7d6b5a6cb3d9/images/lv_5_ex_3.png)


<br>

## 📨 문의 사항
Yujin Kang - kyujin995@gmail.com

Project Link: https://github.com/YJ-Kkang/spa-homework.git
.
