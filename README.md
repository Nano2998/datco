# Datco

## 프로젝트 개요
Datco는 발주 및 견적 관리를 위한 백엔드 서비스입니다.

## 주요 기능
- **사용자 관리**: 회원 가입 및 로그인 (JWT 인증)
- **발주 관리**: 발주 생성, 수정, 삭제
- **견적 관리**: 견적 요청 및 응답
- **파일 업로드**: 디자인 파일 저장 및 관리

## API 문서
Swagger UI에서 API 문서를 확인할 수 있습니다.

🔗 **Swagger URL**: [localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## 환경 변수 설정
이 프로젝트를 실행하기 위해서는 환경 변수 파일을 생성해야 합니다.

### 1. `application.properties` 예시
```properties
spring.application.name=datco

file.upload-dir=${UPLOAD_DIR}

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update

spring.jwt.secret=${JWT_SECRET}
spring.jwt.expires=${JWT_EXPIRES}
spring.jwt.refresh.expires=${JWT_REFRESH_EXPIRES}
```

### 2. `.env` 예시
```env
UPLOAD_DIR=/Users/Desktop/dev/projects/images
DB_HOST=localhost
DB_PORT=3306
DB_NAME=datco
DB_USERNAME=root
DB_PASSWORD=1111
JWT_SECRET=whrugjsmsrndaldifjqnstkghwkfkfdlfnclsqksghdneidvn
JWT_EXPIRES=1800000
JWT_REFRESH_EXPIRES=172800000
```

## 실행 방법
1. `.env` 파일을 생성하고 환경 변수를 설정합니다.
2. `application.properties`에서 환경 변수를 불러오도록 설정합니다.
3. 프로젝트를 빌드하고 실행합니다.

```sh
./gradlew bootRun
