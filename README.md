# page27clone ⚡


page27 클론코딩 프로젝트입니다.


## Part     
------------
총 3명


+ 채창완 : 프론트


+ 이수형 : 백엔드


+ 김찬우 : DB


## 프로젝트 기능


------------


+ 회원가입 & 로그인


+ 상품 주문 및 결제


+ 상품 장바구니 기능, 주문 관리


+ 회원 정보 수정, 주소 등록


+ 관리자 페이지를 통한 회원, 상품, 주문 관리

## 기술 스택


------------


📙  프론트 엔드




📙 백엔드

+ Spring Boot


+ Spring Data JPA


+ Spring Security


+ QueryDsl


+ h2

📙 개발 환경


------------


+ IntelliJ


+ erdCloud


+ VS Code


📙 ScreenShot 및 기능

------------

🔍 (관리자 페이지) 메인 화면 
+ 누적 방문자 수 및 각 관리 현황의 데이터 요약 출력
<p align = "center">
<img src = "https://user-images.githubusercontent.com/73347933/124236466-6d72b500-db51-11eb-82ff-744ecfcc8d35.PNG" width = "90%">

</p>

🔍 (관리자 페이지) 회원, 주문 현황 페이지
+ Querydsl을 통하여 동적 검색 기능, 페이징 기능을 통하여 데이터 조회 


<p align = "center">
<img src="https://user-images.githubusercontent.com/73347933/124240994-4c609300-db56-11eb-9eaa-6952441aebc5.PNG" height="400px" width="45%">
<img src="https://user-images.githubusercontent.com/73347933/124240998-4e2a5680-db56-11eb-93a7-bbc3f21b431f.PNG" height="400px" width="45%">
</p>


🔍 (관리자 페이지) 상품 등록, 현황 페이지
+ Querydsl을 통하여 동적 검색 기능, 상품 등록 및 이미지 파일 자동 저장
+ 상품 판매 상태 변경(품절, 판매), 상품 삭제 및 조회 

<p align = "center">
<img src = "https://user-images.githubusercontent.com/73347933/124242722-03a9d980-db58-11eb-9aa8-bb72e491bd7b.PNG" height = "400px" width = "45%">
<img src = "https://user-images.githubusercontent.com/73347933/124243249-9cd8f000-db58-11eb-8e69-10130f0b003e.PNG" height = "400px" width = "45%">
</p>


🔍 (관리자 페이지) 관리자 비밀번호 변경, 관리자 등급이 아닐 경우 접근 제한 페이지
+ 로그인할 때의 등급에 의해 접근 불가능한 페이지에 접근할 경우, Error 페이지 출력

<p align = "center">
<img src = "https://user-images.githubusercontent.com/73347933/124244465-ed9d1880-db59-11eb-88c8-a41a49588778.PNG" height = "400px" width = "45%">
<img src = "https://user-images.githubusercontent.com/73347933/124244945-71ef9b80-db5a-11eb-95be-2e97d434e04c.PNG" height = "400px" width = "45%">
</p>