# Market Integration Project

## 프로젝트 개요
Market Integration Project는 Spring WebFlux를 사용하여 빗썸(Bithumb) API를 활용한 멀티모듈 프로젝트입니다.<br><br>
현재는 빗썸의 퍼블릭 및 프라이빗 API를 통해 암호화폐 거래소 데이터를 조회하고 관리하는 기능을 제공합니다.<br>
추후 빗썸 외 거래소의 API를 조회하는 모듈을 추가 해 여러 거래소의 데이터를 한번에 조회하고 관리하는 기능을 제공하고자 합니다.

## 주요 기능
- **퍼블릭 API**: 현재 시장 가격, 오더북, 거래 내역 등의 데이터를 조회합니다.
- **프라이빗 API**: 계정 정보, 잔액 조회, 주문 내역, 거래 내역 등을 관리합니다.
- **WebFlux**: 비동기 및 논블로킹 방식으로 고성능의 API 호출을 구현합니다.
- **RabbitMQ**: 메시지 큐를 사용하여 모듈 간 통신을 수행합니다.
- **스케줄러**: 주기적으로 데이터를 갱신하거나 특정 작업을 실행합니다.
- **사용자 Secret 암호화**: 거래소 private API 접근에 필요한 사용자 Secret을 암호화하여 데이터베이스에 안전하게 저장합니다.

## 모듈 구성 및 장점
- **api-broker**: 스케줄러에서 큐로 실행 명령을 받으면 bithumb-api를 호출하고 자체 비즈니스 로직으로 작업을 수행합니다. 이후 저장이 필요한 정보는 JPA를 사용해 DB에 저장합니다.
- **bithumb-api**: WebFlux를 사용하여 비동기 방식으로 빗썸 API를 호출 후 데이터를 반환하는 역할을 합니다.(빗썸 API V1.2.0)
- **spring-scheduler**: 스케줄링 작업을 처리하며, RabbitMQ를 통해 api-broker와 통신합니다.
- **멀티모듈 구성 장점**: 각 모듈이 독립적으로 개발, 테스트, 배포될 수 있어 유지보수성과 확장성이 높습니다.

## 사용 기술
- **Spring Boot 3**
- **Java**
- **JPA**
- **Spring Web**
- **Spring WebFlux**
- **Spring Scheduler**
- **RabbitMQ**
- **Docker (추후 지원 예정)**

## 파일 구조
- **api-broker/**: 실제 비즈니스 로직과 DB 관련 소스 코드
- **bithumb-api/**: 빗썸 API 호출 관련 소스 코드
- **spring-scheduler/**: 스케줄링 관련 소스 코드
- **.gitignore**: Git 무시 파일
- **README.md**: 프로젝트 설명서
- **build.gradle**: Gradle 빌드 파일
- **gradlew**: Gradle Wrapper 실행 파일 (Unix)
- **gradlew.bat**: Gradle Wrapper 실행 파일 (Windows)
- **settings.gradle**: Gradle 설정 파일

## 설치 및 사용 방법
1. **저장소 클론**:
   ```bash
   git clone https://github.com/HWANGMA27/BITHUMB-API.git
   cd BITHUMB-API
2.	**의존성 설치**:
    ```bash
    ./gradlew build
3.	**애플리케이션 실행**:
    ```bash
    ./gradlew bootRun

## 설정
application.yml 파일을 수정하여 API 키, 시크릿 키 및 기타 설정을 입력합니다. 또한, 스케줄러의 크론 타임을 변경할 수 있습니다.
   ```bash
    bithumb:
      api:
        key: "YOUR_API_KEY"
        secret: "YOUR_SECRET_KEY"
      spring:
        rabbitmq:
        host: "localhost"
        port: 5672
      scheduler:
        cron:
          time: "0 0/1 * * * ?"  # 크론 타임 설정
```
## 도커 컴포즈 지원 예정
추후 도커 컴포즈를 지원하여 쉽게 배포하고 관리할 수 있도록 할 예정입니다.

#### 참조 
https://apidocs.bithumb.com/
