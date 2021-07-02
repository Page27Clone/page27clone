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

+ HTML

+ CSS

+ JavaScript

+ Thymeleaf


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

------------

🔍 (메인페이지) 메인화면
+ 주요상품들로 carousel 구성하여 일정시간마다 움직이도록 구현. 사진 클릭시 해당 상품의 상세페이지로 이동.

<p align = "center">
<img src = "https://user-images.githubusercontent.com/69083280/124259453-7c656180-db69-11eb-9a41-b9b862c673ed.JPG" height = "400px" width = "30%">
<img src = "https://user-images.githubusercontent.com/69083280/124259456-7d968e80-db69-11eb-8905-c22f953ba49b.JPG" height = "400px" width = "30%">
<img src = "https://user-images.githubusercontent.com/69083280/124259458-7e2f2500-db69-11eb-8edd-e0b9a6282959.JPG" height = "400px" width = "30%">
</p>

🔍 (메인페이지) 회원가입/로그인
+ 회원가입 폼 정규식 적용 및 중복확인 기능(Ajax)
+ 로그인 기능(Spring Security 적용)

<p align = "center">
<img src = "https://user-images.githubusercontent.com/69083280/124261318-9acc5c80-db6b-11eb-92ba-f91f48bb50f4.JPG" height = "400px" width = "45%">
<img src = "https://user-images.githubusercontent.com/69083280/124261315-999b2f80-db6b-11eb-8bed-bb4b3fd88be0.JPG" height = "400px" width = "45%">
</p>

🔍 (메인페이지) 마이페이지
+ 잔여 마일리지 확인 / 주문상태 확인 / 주문목록, 정보수정, 마일리지, 배송지 목록 PAGE로 이동 가능한 링크 제공

<p align = "center">
<img src = "https://user-images.githubusercontent.com/69083280/124261832-3d84db00-db6c-11eb-8cf4-2455a0fde929.JPG" height = "400px" width = "90%">
</p>

🔍 (메인페이지) 주문목록
+ 주문상태 및 기간에 따라 Querydsl을 통하여 동적 검색 기능, 페이징 기능을 통하여 데이터 조회
+ 주문 취소 / 교환 / 반품 등 주문한 상품의 상태 변경(Ajax)
<p align = "center">
<img src = "https://user-images.githubusercontent.com/69083280/124262464-0d8a0780-db6d-11eb-8156-31ead8b97c11.JPG" height = "400px" width = "90%">
</p>

🔍 (메인페이지) 배송지 목록 / 배송지 등록 / 배송지 수정
+ 배송지 정보 표시 및 선택 삭제
+ 배송지 등록 및 수정 가능

<p align = "center">
  <img src = "https://user-images.githubusercontent.com/69083280/124263421-2f37be80-db6e-11eb-9ff3-8fc43ed0595d.JPG" height = "400px" width = "30%">
  <img src = "https://user-images.githubusercontent.com/69083280/124263416-2e069180-db6e-11eb-96bd-8263c25a47d4.JPG" height = "400px" width = "30%">
  <img src = "https://user-images.githubusercontent.com/69083280/124263422-2fd05500-db6e-11eb-88cb-055f354fe846.JPG" height = "400px" width = "30%">
</p>

🔍 (메인페이지) 회원정보 수정
+ 회원정보 수정 가능
+ 
<p align = "center">
  <img src = "https://user-images.githubusercontent.com/69083280/124263763-a0777180-db6e-11eb-91f9-e4879f27b6db.JPG" height = "400px" width = "90%">
</p>

🔍 (메인페이지) 마일리지
+ 총 적립금, 사용된 적립금, 사용가능한 적립금액 알 수 있다.
+ 마일리지 적립내역 확인, 페이징 기능

<p align = "center">
  <img src = "https://user-images.githubusercontent.com/69083280/124263921-d74d8780-db6e-11eb-895e-6d454cb89f50.JPG" height = "400px" width = "90%">
</p>

