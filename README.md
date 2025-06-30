# Spring Plus

## 1. 프로젝트 개요
...

## 2. API 명세
...

## 3. Health Check API
- 서버의 정상 동작 여부를 확인할 수 있는 API입니다.
- **Endpoint**: `http://[내 탄력적 IP 주소]:8080/health`

## 4. AWS 서비스 설정 내역
설정 내역은 아래와 같습니다.

### 5-1. EC2 (Elastic Compute Cloud)
- **설명**: 애플리케이션 서버
- **설정 화면**:
<img width="1467" alt="EC2 세부 설정" src="https://github.com/user-attachments/assets/abd3f22e-dab6-4bd9-b8ca-a1def6c09be5" />

### 5-2. RDS (Relational Database Service)
- **설명**: MySQL 데이터베이스 RDS
- **설정 화면**:
<img width="1467" alt="RDS 설정" src="https://github.com/user-attachments/assets/e2fad7c0-3a76-4cfa-b09c-f35560b319db" />

### 5-3. S3 (Simple Storage Service)
- **설명**: S3 버킷 속성
- **설정 화면**:
<img width="1470" alt="S3 버킷 속성" src="https://github.com/user-attachments/assets/694eb010-551f-43a6-a18b-c2fcaf5a6031" />

### 5-5. Security Group (보안 그룹)
- **설명**: 각 서비스 간의 보안 그룹
- **EC2 보안 그룹 인바운드 규칙**:
<img width="1468" alt="EC2 보안 그룹" src="https://github.com/user-attachments/assets/b0d7f5f3-e760-4c45-a3ca-223037d3f9fd" />

- **RDS 보안 그룹 인바운드 규칙**:
<img width="1470" alt="RDS 보안 그룹" src="https://github.com/user-attachments/assets/05c99b0e-d4a1-42f0-b798-7941ee5d50c3" />
