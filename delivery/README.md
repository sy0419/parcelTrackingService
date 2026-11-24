# Parcel Tracking Service (배송 조회 서비스)

송장번호(`invoiceNumber`)를 기준으로 배송을 등록하고 조회하며, 배송 상태 전이를 관리하는 Spring Boot 기반 REST API 및 프론트엔드 연동 프로젝트입니다.

## 목차

1. 기능 개요
2. 기술 스택
3. 주요 구현 내용
4. 설치 및 실행 방법
5. API 명세
6. 예외 처리
7. 향후 개선 계획 (TODO)
8. 테스트 방법

## 1. 기능 개요

- 송장 등록 (`POST /deliveries`)
- 송장번호 기반 배송 단건 조회 (`GET /deliveries/{invoiceNumber}`)
- 배송 상태 변경 (`PUT /deliveries/{invoiceNumber}/status`)
- 단방향 배송 상태 전이 제어 (`PREPARING` -> `SHIPPED` -> `IN_TRANSIT` -> `DELIVERED`)
- 프론트엔드 간단 UI 제공 (송장번호 입력 -> 배송 상태 및 도착 예정일 조회)

## 2. 기술 스택

- **백엔드:** Java 21, Spring Boot 3.4.9, Spring Data JPA, Lombok, MySQL
- **프론트엔드:** HTML, JavaScript (`fetch` API)
- **도구:** Visual Studio Code, Maven, Git / GitHub, HTTP 클라이언트(Postman / `.http`)

## 3. 주요 구현 내용

- `Delivery` 엔티티(`invoiceNumber` PK, `DeliveryStatus` Enum `@Enumerated(EnumType.STRING)`, `expectedArrivalDate`, `createdAt`, `updatedAt`) 및 `DeliveryRepository` 구성
- `DeliveryStatus` Enum의 `canMoveTo` 메서드를 통한 단방향 상태 변경 제어(`DELIVERED` 상태에서는 추가 전이 불가)
- `DeliveryController` 구현 및 로컬 프론트엔드(`http://localhost:5500`) 연동을 위한 `@CrossOrigin` 설정
- `index.html`, `main.js`를 통한 송장 조회 결과 및 오류 메시지 표시

## 4. 설치 및 실행 방법

### 사전 준비

- Java 21 설치
- MySQL 서버 실행 및 데이터베이스(`delivery_db`) 준비
- Maven 설치 (또는 Maven Wrapper `./mvnw` 사용)

### 설치 및 실행

1. 저장소 클론 및 백엔드 디렉토리 이동
   - `git clone [https://github.com/sy0419/parcelTrackingService.git](https://github.com/sy0419/parcelTrackingService.git)`
   - `cd parcelTrackingService/Backend`

2. `src/main/resources/application.properties`에서 데이터베이스 연결 정보 설정
   - `spring.datasource.url=jdbc:mysql://localhost:3306/delivery_db?serverTimezone=UTC&useSSL=false&characterEncoding=UTF-8`
   - `spring.datasource.username=your_username`
   - `spring.datasource.password=your_password`
   - `spring.jpa.hibernate.ddl-auto=update`
   - `server.port=8080`

3. 애플리케이션 실행
   - `./mvnw spring-boot:run`
   - 서버가 실행되면 기본 주소 `http://localhost:8080`에서 API를 사용할 수 있습니다.

## 5. API 명세

| 메서드 | 경로 | 설명 | 요청 바디 예시 |
| :--- | :--- | :--- | :--- |
| `POST` | `/deliveries` | 송장 등록 | `{ "invoiceNumber": "TEST123456", "status": "PREPARING" }` |
| `GET` | `/deliveries/{invoiceNumber}` | 송장번호로 배송 조회 | - |
| `PUT` | `/deliveries/{invoiceNumber}/status` | 배송 상태 변경 | `{ "newStatus": "SHIPPED" }` |

## 6. 예외 처리

- `@RestControllerAdvice` 기반 `GlobalExceptionHandler`에서 `DeliveryNotFoundException` 발생 시 `404 Not Found` 상태 코드와 함께 `timestamp`, `status`, `error`, `message` JSON 응답을 반환합니다.

## 7. 향후 개선 계획 (TODO)

- 잘못된 상태 전이(`InvalidStatusChangeException`)에 대한 `400 Bad Request` 전역 예외 핸들러 추가
- `DeliveryService` 계층의 도착 예정일(`expectedArrivalDate`) 계산 및 상태 변경 로직 보완
- 중복 송장 등록 방지 및 요청 값 검증 강화
- 사용자/관리자 인증 및 권한 관리(JWT/OAuth 등) 도입
- 단위/통합 테스트 추가 및 Docker/CI/CD 구성

## 8. 테스트 방법

1. 백엔드 서버(`http://localhost:8080`)를 먼저 실행합니다.
2. 별도 터미널에서 프론트엔드 정적 서버를 실행합니다.
   - `cd delivery/Frontend`
   - `python -m http.server 5500`
3. `http://localhost:5500`에 접속해 송장번호를 입력하고 **조회** 버튼을 눌러 결과를 확인합니다.
   - `file://`로 HTML 파일을 직접 열면 브라우저 CORS 정책으로 요청이 차단될 수 있으므로 반드시 HTTP 서버를 통해 실행하세요.
   - 프론트엔드와 백엔드의 포트(`5500` / `8080`)가 다르므로 컨트롤러의 `@CrossOrigin` 설정이 유지되어야 합니다.
