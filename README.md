# KnockKnock
협업 인원 : 개인 (기여도 100%)           
2021. 12~ 진행중 
*****************************************
## 🌳소개
### 똑똑하게 하는 정보 거래, KnockKnock 
일상에서 늘 필요하지만 쉽게 얻을 수 없었던 정보를 위치 기반으로 받아볼 수 있으면 좋겠다는 생각을 했습니다. 
위치 기반으로 게시판 형태의 지역 커뮤니티는 많았지만 실시간으로 필요한 정보가 활발히 공유되지 않고, 직접적으로 정보 요청이 전달되지 않는다는 아쉬움이 있었습니다.
KnockKnock(똑똑) 서비스는 우리 동네 카페 잔여석, 동네 포장마차의 운영 여부 등 위치 기반의 정보들을 공유하고, 
지정한 위치 인근의 유저들에게 정보 요청 메세지를 보내 즉각적인 정보 공유를 가능하게 하는 어플리케이션입니다.
활발한 정보 공유를 위해 공유 포인트를 적립하는 방식으로 운영되며 추후에 포인트 결제 시스템까지 도입하고자 합니다.

## 🌳시스템 구성도 
![image](https://user-images.githubusercontent.com/84822464/160988781-911321a4-01bb-40d2-94f2-ebfb2e388815.png)
### 테이블 구조 
![image](https://user-images.githubusercontent.com/84822464/161010206-f29ebdc5-9568-4d9b-b454-d9acf8cb0eec.png)
## 🌳환경
### 🌷 개발환경
AWS RDS for MariaDB(RDB), Spring Boot(API Server), Android(Front), Firebase(Real Time DB- 실시간 유저 위치 업데이트), Google Map API, Amazon S3(이미지 조회, 저장)
### 🌷 버전 관리 시스템 
Git
## 🌳Using
Spring Boot, Gradle(Build Tool), Spring Data JPA, QueryDSL, MariaDB(RDB), Spring Web Socket(STOMP), Java 8(Language), IntelliJ IDEA

## 🌳주요 기능 
https://github.com/eeHeaven/KnockKnock_Android/blob/master/README.md#function



