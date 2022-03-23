# Encore 1팀 Project - BackEnd
 
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


# AWS.YML 

![image](https://user-images.githubusercontent.com/87063105/159701719-f049e518-a0b0-43b1-8e09-9e49ece8c999.png)

현재 저희 서비스는 AWS S3 버킷을 사용해서 이미지를 관리하고 있습니다.

해당 스프링 백엔드를 실행하기 위해서는 aws.yml파일을 resources 디렉토리에 추가해야 합니다.

aws.yml 파일은 현재 보안상 gitignore에 등록되어 있습니다. 따로 aws s3에 대해서 등록을 하셔야 사용 및 빌드가 가능합니다.

AWS-S3는 버킷 등록 , AWS-IAM 등록을 하신 후에 ACCESSKEY와 SECRETKEY를 받아서 사용하시면 됩니다.

[AWS-S3 링크](https://aws.amazon.com/ko/free/?all-free-tier.sort-by=item.additionalFields.SortRank&all-free-tier.sort-order=asc&awsf.Free%20Tier%20Categories=categories%23storage&trk=919c3162-c8f1-4d4c-baec-33fb3fcc1988&sc_channel=ps&sc_campaign=acquisition&sc_medium=ACQ-P|PS-GO|Brand|Desktop|SU|Storage|S3|KR|EN|Text&s_kwcid=AL!4422!3!489215169070!e!!g!!aws%20s3&ef_id=CjwKCAjwiuuRBhBvEiwAFXKaNNZi6zFbD2CY43lm4PliyIvG8Zg7tKbm56JJz5oFJvOxWrAahmawHhoCSVgQAvD_BwE:G:s&s_kwcid=AL!4422!3!489215169070!e!!g!!aws%20s3&awsf.Free%20Tier%20Types=*all)


```
cloud:
  aws:
    credentials:
      accessKey: [AWS-S3-BUCKET-ACCESSKEY]
      secretKey: [AWS-S3-BUCKET-SECRETKEY]
    s3:
      bucket: pinter-bucket
    region:
      static: ap-northeast-2
    stack:
      auto: false

spring:
  servlet:
    multipart:
      max-file-size: 2MB
      max-request-size: 2MB
      resolve-lazily: true

logging:
  level:
    com:
      amazonaws:
        util:
          EC2MetadataUtils: error

```

# flow chart

https://app.diagrams.net/#HPinterest-clone-playdata%2Fpinterest-clone%2Fdevelop%2FUntitled%20Diagram.drawio
![image](https://user-images.githubusercontent.com/87063105/159701521-58fe0678-d831-4645-82d3-b39ead19dd42.png)


