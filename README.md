﻿# Encore 1팀 Project - BackEnd
 
 현재 Repository에는 Local로만 구현이 되어 있습니다.
 
 - back end (server)
  - Java 11 
  - spring framework (spring boot 2.5.10)
  - gradle (7.4)
  - spring jpa (2.5.10)
  - queryDSL (5.0)
  - DB
    - H2
    - PostgreSQL ( 아직 도입 안했음 )
-  Amazon Web Service S3

이러한 기술을 사용하였습니다.


또한 해당 프로젝트가 나아갈 방향에 대한 그림입니다.

 ![pintereststack 001](https://user-images.githubusercontent.com/89529028/159158762-28a7169a-5b59-4367-a742-6e32fcffc1e0.jpeg)
 
 
# 데이터 베이스 DDL 
![pinterest_db_diagram](https://user-images.githubusercontent.com/89529028/159424201-9906c884-deda-4bb9-b465-0a9bc9c3865e.png)


# API 구분
![image](https://user-images.githubusercontent.com/87063105/159651654-6deffcc4-e065-4c94-9a59-b4e17640fdb7.png)

![image](https://user-images.githubusercontent.com/87063105/159651740-49b7a295-494b-4854-b014-ed5b4de369d5.png)

![image](https://user-images.githubusercontent.com/87063105/159651759-3c4a5673-28db-4389-a8e4-13e406fb8efa.png)

![image](https://user-images.githubusercontent.com/87063105/159651781-2e76c0bf-6305-4de4-b8a3-7ae8dbd90b69.png)

![image](https://user-images.githubusercontent.com/87063105/159651808-ab25e106-3988-406b-b04d-d70e5106375b.png)

![image](https://user-images.githubusercontent.com/87063105/159651834-3c655228-d455-49a4-83c5-cf90de4a8ca5.png)

![image](https://user-images.githubusercontent.com/87063105/159651872-f13ab5b8-b590-4828-9c21-3671f1656947.png)

# JWT 토큰 도입

하지만 JWT 토큰은 현재 시간상 문제로 급하게 작성되어 Refresh Token 구현이 아직 되어있지 않습니다.
