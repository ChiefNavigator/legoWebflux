# Reactive Web
Reactive Web 는 고객의 정보를 관리하는 서비스 입니다.

***  

## 1. Develop Period
2024-02-24 ~ 2024-03-02

## 2. Contributor
@ChiefNavigator

## 3. Function
- 고객 정보 등록
- 고객명으로 검색
- 전화번호로 검색
- 고객 정보 삭제

## 4. How to Test
- [ApiApplication.java](api-application/src/main/java/com/lego/apiApplication/ApiApplication.java) 를 실행합니다.
- [EmployeeRestConroller.http](api-application/http/EmployeeRestController.http) 를 통해 차례대로 HTTP 통신을 수행하며 테스트합니다.
***

## 5. Environment
- IDE : IntelliJ IDEA 2023.1.5
- JAVA : Java 17
- JDK : Amazon Corretto version 19.0.2
- Framework : SpringBoot 3.0.2, Spock 2.3-groovy-3.0
- Database :
- ORM :

## 6. System Architecture
![image](images/reactiveweb-architecture.png)

## 7. Data Structure
- Trie 구현체 : [Trie.java](resource/src/main/java/com/lego/resource/datastructure/Trie.java)
- Repository CRUD 구현체 : [EmployeeRepositoryImpl.java](resource/src/main/java/com/lego/resource/jpa/employee/EmployeeRepositoryImpl.java)
- 이미지에서 실선은 연결된 것이며, 점선은 중간 노드가 생략된 것을 의미 합니다.
![image](images/reactiveweb-dataStructure.png)


## 8. Core Function Logic
### 8.1 고객정보 등록
![image](images/employeeRegisterLogic.png)
### 8.2 고객명으로 검색
![image](images/findByNameLogic.png)
### 8.3 전화번호로 검색
![image](images/findByPhoneNumberLogic.png)
### 8.4 고객정보 삭제
![image](images/deleteEmployeeLogic.png)


<br><br>
***  
## Plan (2024-02-24 ~ 2024-02-27)
- [x] [필수] Phase 1 : 2024-02-24 ~ 2024-02-24
- [x] [필수] Phase 2 : 2024-02-24 ~ 2024-02-25
- [x] [필수] Phase 3 : 2024-02-25 ~ 2024-02-25
- [x] [필수] Phase 4 : 2024-02-25 ~ 2024-03-02

### [필수] Phase 1 - 프로젝트 디자인 및 개발 환경 구성
- [x] 요구사항 확인
- [x] 기본 기능 구상
- [x] 시스템 아키텍처 디자인
- [x] Layered Architecture 개발 환경 구성

### [필수] Phase 2 - Memory Bases 기능 구현
- [x] 고객 등록
- [x] 고객명으로 검색
- [x] 전화번호로 검색
- [x] 고객 정보 삭제

### [필수] Phase 3 - JPA Bases 기능 구현
- [x] 고객 등록
- [x] 고객명으로 검색
- [x] 전화번호로 검색
- [x] 고객 정보 삭제

### [필수] Phase 4 - R2DBC Bases 기능 구현
- [x] 고객 등록
- [x] 고객명으로 검색
- [x] 전화번호로 검색
- [x] 고객 정보 삭제