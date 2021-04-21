## 학원관리 시스템

### 강사 / 학생 / 관리자의 입장에서 학원 이용 / 관리에 필요한 서비스를 제공

### 기술 스택

- Back-End : Java(Spring Boot)
- Template Engine : Thymeleaf
- DBMS : MySQL
- Persistence Framework : JPA(Spring Data JPA, Querydsl)

### 기능

#### 공통
- 로그인 / 회원가입 / 아이디 찾기 / 비밀번호 찾기
- 개인정보 변경

#### 학생
- 수강 신청
- 질문 / 과제 / 상담 게시물 등록
- 본인 출석 확인

#### 강사
- 강의 등록 신청
- 출석
- 질문 / 과제 / 상담 / 강의 공지 게시물 등록
- 질문 답변

#### 관리자
- 강의 신청 / 강사 등록 허가
- 상담 답변 등록