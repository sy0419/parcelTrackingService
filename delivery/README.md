# Parcel Tracking Service (배송 조회 서비스)

이 프로젝트는 **택배 송장 번호 기반 배송 추적 서비스**를 제공하는 Spring Boot 기반 애플리케이션입니다.  
사용자와 관리자를 위한 기능이 포함되어 있고, 기본 CRUD 및 상태 변경 로직을 포함하고 있습니다.

---

## 목차

1. 기능 개요  
2. 기술 스택  
3. 아키텍처 / 설계  
4. 설치 및 실행 방법  
5. API 문서  
6. 예외 처리 / 유효성 로직  
7. 개선 계획 / TODO  
8. 라이선스  

---

## 1. 기능 개요

- 송장 등록 (POST)  
- 송장 조회 (GET)  
- 관리자에 의한 상태 변경 (PUT)  
- 배송 상태 흐름 제어 (예: 준비 → 배송 시작 → 배송 중 → 배송 완료)  
- 도착 예정일 자동 계산  
- 프론트엔드 간단 UI (송장 입력 → 상태 조회)

---

## 2. 기술 스택

- **백엔드:**  
  - Spring Boot  
  - Spring Data JPA  
  - Lombok  
  - MySQL  

- **프론트엔드:**  
  - HTML / CSS / JavaScript (간단 UI)  

- **도구:**  
  - Visual Studio Code  
  - Postman (API 테스트)  
  - Git / GitHub  

---

## 3. 아키텍처 / 설계

- `Delivery` 엔티티를 중심으로 배송 정보를 관리  
- 배송 상태는 ENUM 타입으로 정의 (`PREPARING`, `SHIPPED`, `IN_TRANSIT`, `DELIVERED`)  
- 관리자 권한으로 상태 변경 가능하며, 상태 전이 검증 로직 포함  
- 출발일을 기준으로 예상 도착일 자동 계산 기능  
- RESTful API 설계 및 간단한 프론트엔드와 연동  

---

## 4. 설치 및 실행 방법

### 사전 준비

- Java 11 이상 설치  
- MySQL 서버 구동 및 데이터베이스 생성  
- Maven (또는 Gradle) 설치 (Maven Wrapper 사용 시 별도 설치 필요 없음)  

### 설치 및 실행

1. 저장소 클론

```bash
git clone https://github.com/sy0419/parcelTrackingService.git
cd parcelTrackingService/Backend
```

2. application.properties 또는 application.yml 파일에서 MySQL 연결 정보 수정

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name?serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. 빌드 및 실행

```bash
# Maven 사용 시
./mvnw spring-boot:run

# 또는 JAR 실행
mvn clean package
java -jar target/parcel-tracking-service.jar
```

4. 서버가 정상적으로 실행되면 기본 URL (http://localhost:8080)에서 API 사용 가능

---

## 5. API 문서

아래는 주요 엔드포인트 예시입니다:

| 메서드 | 경로                             | 설명                   | 요청 바디 / 파라미터                    | 응답 예시                                       |
|--------|----------------------------------|------------------------|----------------------------------------|------------------------------------------------|
| POST   | `/api/deliveries`                | 송장 등록              | `{ "trackingNumber": "12345" }`        | `{ "id": 1, "trackingNumber": "12345", "status": "PREPARING", ... }` |
| GET    | `/api/deliveries/{id}`           | 배송 상태 조회 (ID 기준) | –                                      | `{ "id": 1, "trackingNumber": "12345", "status": "IN_TRANSIT", "expectedDeliveryDate": "2025-10-20", ... }` |
| GET    | `/api/deliveries/tracking/{trackingNumber}` | 배송 상태 조회 (송장번호 기준) | –                                  | `{ "id": 1, "trackingNumber": "12345", "status": "IN_TRANSIT", "expectedDeliveryDate": "2025-10-20", ... }` |
| PUT    | `/api/deliveries/{id}/status`   | 관리자용 상태 변경      | `{ "status": "DELIVERED" }`             | `{ "id": 1, "status": "DELIVERED", ... }`      |

> **유의사항**  
> - 상태 변경 시 유효한 전이인지 검증  
> - 존재하지 않는 ID나 잘못된 요청은 적절한 예외 응답 반환  

---

## 6. 예외 처리 / 유효성 로직

- 송장번호 중복 등록 방지  
- 존재하지 않는 배송 조회 시 `404 Not Found` 반환  
- 잘못된 상태 전이 요청 시 `400 Bad Request` 반환  
- 요청 바디 검증 실패 시 상세 오류 메시지와 함께 `400 Bad Request` 반환  
- 기타 서버 내부 오류 발생 시 `500 Internal Server Error` 반환  
- 모든 예외는 사용자에게 이해하기 쉬운 메시지로 전달  

---

## 7. 개선 계획 / TODO

- 사용자 인증 및 권한 관리 추가 (예: JWT, OAuth)  
- 관리자용 대시보드 UI 개발  
- 더 다양한 배송 상태 및 상세 정보 추가  
- 배치 작업으로 자동 상태 업데이트 기능 구현  
- 배송 알림 푸시 / 이메일 연동  
- 프론트엔드 SPA 프레임워크 적용 (React, Vue 등)  
- 테스트 코드 보완 및 자동화 (Unit, Integration 테스트)  
- 도커(Docker) 컨테이너화 및 CI/CD 파이프라인 구축  

---

## 8. 테스트하기
1. 백엔드 서버 실행

```bash
cd delivery/Backend
./mvnw spring-boot:run
```
- 백엔드가 기본 포트 8080에서 실행됩니다.
- Postman 또는 test.http 파일을 통해 송장번호 등록, 조회, 상태 변경 API를 테스트할 수 있습니다.

2. 프론트엔드 서버 실행

```bash
cd delivery/Frontend
python -m http.server 5500
```
- http://localhost:5500 주소로 프론트엔드가 실행됩니다.
- VSCode Live Server 확장 또는 Python 내장 서버 중 편한 방법을 사용합니다.
- 백엔드 실행 후 다른 터미널에 실행합니다.

3. 실제 테스트 시나리오

1. **송장번호 등록**  
   백엔드 API (Postman 또는 `test.http`)를 통해 배송 정보를 먼저 등록합니다.

2. **프론트엔드 접속**  
   브라우저에서 `http://localhost:5500`에 접속해 `index.html` 페이지를 엽니다.

3. **배송 조회**  
   등록한 송장번호를 입력 후 “조회” 버튼을 클릭하면 백엔드 API와 통신하여 배송 상태와 도착 예정일을 화면에 표시합니다.

4. 주의 사항

- 프론트엔드와 백엔드는 서로 다른 포트에서 실행되므로, 백엔드 컨트롤러에 `@CrossOrigin(origins = "http://localhost:5500")` 설정이 반드시 필요합니다.
- `file://` 경로로 직접 HTML 파일을 열 경우, 브라우저의 보안 정책(CORS) 때문에 API 요청이 차단될 수 있습니다. 반드시 서버를 통해 실행하세요.
- 오류 발생 시 백엔드 로그와 브라우저 개발자 도구(콘솔)에서 상세 에러 메시지를 확인하면 문제 해결에 도움이 됩니다.
