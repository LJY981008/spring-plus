# Spring Plus

## 1. 프로젝트 개요

- **프레임워크**: Spring Boot 3.x
- **데이터베이스**: MySQL (AWS RDS)
- **파일 저장소**: AWS S3
- **배포 환경**: AWS EC2
- **인증**: JWT 기반 인증

### 주요 기능
- 🔐 회원가입/로그인 (JWT 토큰 기반)
- 📝 Todo 생성, 조회, 검색
- 💬 댓글 기능
- 👤 프로필 이미지 업로드/삭제 (S3 연동)
- 🏥 Health Check API

## 2. API 명세

### 인증 API
- `POST /auth/signup` - 회원가입
- `POST /auth/signin` - 로그인

### Todo API
- `POST /todos` - Todo 생성
- `GET /todos` - Todo 목록 조회
- `GET /todos/{todoId}` - Todo 상세 조회
- `GET /todosSearch` - Todo 검색

### 댓글 API
- `POST /todos/{todoId}/comments` - 댓글 작성
- `GET /todos/{todoId}/comments` - 댓글 목록 조회

### 사용자 API
- `GET /users/{userId}` - 사용자 정보 조회
- `PUT /users` - 비밀번호 변경
- `POST /users/profile-image` - 프로필 이미지 업로드
- `DELETE /users/profile-image` - 프로필 이미지 삭제

### 매니저 API
- `POST /todos/{todoId}/managers` - 매니저 할당
- `GET /todos/{todoId}/managers` - 매니저 목록 조회
- `DELETE /todos/{todoId}/managers/{managerId}` - 매니저 삭제

## 3. Health Check API

- **Endpoint**: `GET /health`
- **응답**: `"ok"` (서버 정상 동작 확인)
- **접근 권한**: 누구나 접근 가능 (JWT 토큰 불필요)
- **용도**: 서버 Live 상태 확인, 로드밸런서 헬스체크

## 4. AWS 서비스 설정 내역

### 4-1. EC2 (Elastic Compute Cloud)
- **설명**: 애플리케이션 서버
- **포트**: 8080 (Spring Boot 기본 포트)
- **외부 접속**: 탄력적 IP 설정으로 외부 접근 가능

**설정 화면**:
<img width="1467" alt="EC2 서버 설정" src="https://github.com/user-attachments/assets/abd3f22e-dab6-4bd9-b8ca-a1def6c09be5" />

### 4-2. RDS (Relational Database Service)
- **설명**: MySQL 데이터베이스 RDS
- **연결**: EC2에서만 접근 가능하도록 보안 그룹 설정

**설정 화면**:
<img width="1467" alt="RDS 설정" src="https://github.com/user-attachments/assets/e2fad7c0-3a76-4cfa-b09c-f35560b319db" />

### 4-3. S3 (Simple Storage Service)
- **설명**: 프로필 이미지 저장소
- **파일 크기 제한**: 5MB
- **지원 형식**: JPG, JPEG, PNG, GIF

**설정 화면**:
<img width="1470" alt="S3 버킷 속성" src="https://github.com/user-attachments/assets/694eb010-551f-43a6-a18b-c2fcaf5a6031" />

### 4-4. Security Group (보안 그룹)
- **설명**: 각 서비스 간의 보안 그룹 설정

**EC2 보안 그룹 인바운드 규칙**:
<img width="1468" alt="EC2 보안 그룹" src="https://github.com/user-attachments/assets/b0d7f5f3-e760-4c45-a3ca-223037d3f9fd" />

**RDS 보안 그룹 인바운드 규칙**:
<img width="1470" alt="RDS 보안 그룹" src="https://github.com/user-attachments/assets/05c99b0e-d4a1-42f0-b798-7941ee5d50c3" />

## 5. 배포 및 실행

### 환경변수 설정
```bash
# EC2 환경변수
export DB_USERNAME=admin
export DB_PASSWORD=your-password
export DB_SCHEME=spring_plus
export SECRET_KEY=your-secret-key
export AWS_ACCESS_KEY=your-access-key
export AWS_SECRET_KEY=your-secret-key
export AWS_REGION=ap-northeast-2
export AWS_S3_BUCKET=your-bucket-name
```

### 애플리케이션 실행
```bash
# JAR 파일 실행
java -jar -Dspring.profiles.active=aws expert-0.0.4-SNAPSHOT.jar
```

## 6. 프로젝트 정보

- **프로젝트명**: Spring Plus
- **버전**: 0.0.4-SNAPSHOT


## 조회 성능 개선 결과

아래는 Postman을 이용해 username으로 유저를 조회한 결과와 속도이다.
### 일반 조회: 450ms
<img width="1036" alt="일반 조회" src="https://github.com/user-attachments/assets/555f3314-f18e-41bf-9e97-ac9494bf6db3" />

### 인덱스 조회: 144ms
<img width="1037" alt="인덱스 조회" src="https://github.com/user-attachments/assets/c4af1ecd-18ff-4a00-816e-4d1b830eee56" />

### 인덱스 + 쿼리 프로젝션 조회: 128ms
<img width="1023" alt="인덱스 + 쿼리 프로젝션 적용" src="https://github.com/user-attachments/assets/44272601-494a-4a99-8f6f-61655d3bc535" />




