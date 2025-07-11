# PerFortival

> **PerFortival**은 JSP/Servlet 기반의 개인 프로젝트로, 공연·페스티벌 예매 시스템을 통해 실무 수준의 예약 로직, 후기 기능, 관리자 관리를 구현했습니다.

---

## 프로젝트 개요

**PerFortival**은 혼잡한 공연 티켓 예매 과정을 효율적으로 처리하기 위해 개발된 개인 프로젝트입니다. 프론트엔드 JSP와 백엔드 Servlet 구조를 활용하여 회원 인증, 다양한 예매 방식, 실시간 좌석 배치, 리뷰 게시판 및 관리자 대시보드를 포함한 포괄적 기능을 제공합니다.

**주요 사용 사례**

* 사용자는 다양한 공연 정보를 조회하고, 좌석형·혼합형·자유석 예매 중 선택
* 관리자 계정으로 공연 등록, 구역별 가격 설정, 예매 로그 관리를 수행

---

## 주요 기능

| **카테고리**    | **주요 기능**                                                                   |
| ----------- | --------------------------------------------------------------------------- |
| **회원 관리**   | 회원가입 · 로그인 · 로그아웃 · 마이페이지 · 세션 인증 · 아이디/비밀번호 찾기 · 주소 API 저장                 |
| **공연 조회**   | 공연 목록 조회 · 공연명 검색 · 기간별 필터링 · 공연 상세 페이지 · 관리자 승인 필터링                        |
| **예매 처리**   | 좌석형 예매(자동 좌석 배치 · 최대 2매 연속 선택) · 지정석+스탠딩 예매 · 자유석 예매(수량·가격 선택)              |
| **결제 및 취소** | 총 금액 계산 및 확정 · DB 저장 · 취소 시 회수 로직 · 예매 내역 조회/취소                             |
| **후기 시스템**  | 후기 작성/수정/삭제 · 공연 전/후 구분 · 별점 필수 입력 및 평균 출력 · 정렬/필터 · 댓글(작성/수정/삭제) · 좋아요/싫어요 |
| **관리자**     | 공연 등록/수정/삭제 · 예매 방식 및 가격 설정 · KOPIS API 연동 준비 · 예매 로그 모니터링 · 댓글 삭제          |

## 특장점 및 차별화

* **다양한 예매 방식**: 좌석형·혼합형·자유석을 단일 애플리케이션에서 유연하게 처리하여 사용자 경험을 최적화
* **실시간 좌석 자동 생성**: 공연장 유형별(콘서트·뮤지컬·혼합) 좌석 자동 배치 알고리즘 적용
* **KOPIS API 연동 구조**: 외부 API를 통해 실시간 공연 정보를 가져올 수 있는 확장성 확보
* **심화 리뷰 시스템**: 공연 전/후 후기 구분, 평점 및 댓글·좋아요/싫어요 기능으로 사용자 참여 유도
* **관리자 대시보드**: 공연 관리 및 예매 로그 모니터링으로 운영 효율성 강화

---

## 기술 스택

* **Backend**: Java 17, Servlet API 4.0, JSP, JSTL
* **Database**: MySQL 8.x (DDL/DML 스크립트 포함)
* **Build & Deployment**: Apache Tomcat 10, Maven
* **Frontend**: HTML5, CSS3, Vanilla JavaScript (ES6)
* **협업 & 버전 관리**: Git, GitHub

---

## 프로젝트 구조

```text
PerFortival/
├── src/main/java/com/perfortival/
│   ├── admin/           # 관리자 컨트롤러 & 서비스
│   ├── common/          # ConfigUtil, DBUtil
│   ├── member/          # 회원 Controller, DAO, DTO
│   ├── performance/     # 공연 Service, DAO, DTO
│   ├── reservation/     # 예매 Service, DAO, DTO
│   └── review/          # 후기 Service, DAO, DTO
├── src/main/webapp/
│   └── WEB-INF/views/   # JSP (member, performance, reservation, review, admin)
├── sql/                 # schema.sql, data.sql
├── config.properties    # DB & API 설정
├── pom.xml              # Maven 설정
└── README.md            # 프로젝트 소개
```

---

## 설치 및 실행

1. 레포지토리 클론

   ```bash
   git clone https://github.com/DevLucia-21/PerFortival.git
   cd PerFortival
   ```
2. 환경 설정

   * `config.properties`에 MySQL 연결 정보 및 KOPIS API 키 입력
   * MySQL에서 `sql/schema.sql`과 `sql/data.sql` 실행
3. 빌드 및 배포

   ```bash
   mvn clean package
   ```

   * `PerFortival.war` 파일을 Tomcat `webapps/`에 배포
4. 서비스 접속

   * `http://localhost:8080/PerFortival`

---

## 개발 로드맵

* Spring MVC + REST API 전환
* React/Vue 기반 SPA 프론트엔드 적용
* PG사 연동 실결제 기능 추가
* AWS 배포 및 CI/CD 파이프라인 구축

---

## 작성자

**pye** – 개인 프로젝트 완료 (2025.06)

---

## 라이선스

MIT License © 2025 pye
