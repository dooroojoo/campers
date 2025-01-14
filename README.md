# campers
KH 정보교육원 파이널프로젝트 'CAMPERS'
<hr>

#### 캠핑장 정보, 후기, 커뮤니티, 예약, 결제 서비스를 제공하는 사이트들이 분산되어 있어<br>
#### 사용자의 편의성이 떨어진다는 분석으로 캠핑장 서비스를 통합적으로 제공하는 원스톱 플랫폼입니다.
<br>

## 개발일정
- 2021.11.24 ~ 2021.12.06 프로젝트 기획<br>
- 2021.12.07 ~ 2021.12.13 UI 설계<br>
- 2021.12.14 ~ 2021.12.24 DB 설계<br>
- 2021.12.25 ~ 2022.01.03 UML 설계<br>
- 2021.12.30 ~ 2022.01.19 프로젝트 기능 구현<br>
- 2022.01.24 프로젝트 발표<br>
<br>

## 개발환경
- 운영체제 : Windows10
- 개발도구 : SpringToolSuite4
- 프레임워크 : Spring Boot, Mybatis, Maven, Bootstrap, Spring Security
- DBMS : Oracle DB - SQL Developer
- Server : Apache Tomcat 8.5
- 언어 및 기술 : Java, HTML5, CSS3, Javascript, jQuery, ajax, Thymeleaf
- 협업툴 : Draw.io, ERDcloud, Figma, GitHub
- API : summernote, I’mport, kakao login
<br>


## 팀원 & 맡은기능

- <a href="https://github.com/dooroojoo">김민주</a> : 메인페이지, 캠핑장검색, 고객센터
- 김광중 : 마이페이지, 회원정보 수정, 탈퇴, 숙소등록
- 김보미 : 관리자페이지, 숙소 상세 및 객실 상세페이지, 신고하기
- 정온화 : 예약 및 결제 페이지
- 추현정 : 로그인,회원가입, 자유게시판
<br>

## 설계 

- #### ERD <a href="src/main/resources/static/resources/images/search/erd2.png"> Click</a>
- #### Class Diagram - main<a href="src/main/resources/static/resources/images/search/class-main.png"> Click</a>
- #### Class Diagram - searchCamp <a href="src/main/resources/static/resources/images/search/search-main.png"> Click</a>
- #### Process Diagram <a href="src/main/resources/static/resources/images/search/process.png"> Click</a>
- #### Sequence Diagram <a href="src/main/resources/static/resources/images/search/sequence.png"> Click</a>

<br>

## 김민주 맡은 기능
### 1. <a href="https://github.com/dooroojoo/campers/tree/master/src/main/java/com/cp/campers/search#%EA%B9%80%EB%AF%BC%EC%A3%BC---%EB%A9%94%EC%9D%B8%ED%8E%98%EC%9D%B4%EC%A7%80-%EA%B8%B0%EB%8A%A5-%EC%86%8C%EA%B0%9C"> 메인 페이지</a> : 검색 기능 + SELECT 리스트 출력
### 2. <a href="https://github.com/dooroojoo/campers/tree/master/src/main/java/com/cp/campers/search#%EA%B9%80%EB%AF%BC%EC%A3%BC---%EC%BA%A0%ED%95%91%EC%9E%A5-%EA%B2%80%EC%83%89-%EA%B8%B0%EB%8A%A5-%EC%86%8C%EA%B0%9C"> 캠핑장 검색 기능</a> : 캠핑장 전체 조회 & 조건 검색
### 3. <a href="https://github.com/dooroojoo/campers/tree/master/src/main/java/com/cp/campers/search#%EA%B9%80%EB%AF%BC%EC%A3%BC---%EA%B3%A0%EA%B0%9D%EC%84%BC%ED%84%B0-%ED%8E%98%EC%9D%B4%EC%A7%80-%EC%86%8C%EA%B0%9C"> 고객센터 페이지</a> : 자주 묻는 질문 + 고객센터 안내



