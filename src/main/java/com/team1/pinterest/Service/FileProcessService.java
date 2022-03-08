package com.team1.pinterest.Service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileProcessService {

    private final AwsS3Service awsS3Service;

    public String uploadImage(MultipartFile file) throws IOException{
        String fileName = createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()){
            awsS3Service.uploadFile(inputStream, objectMetadata, fileName);
        } catch (IOException e){
            throw new IllegalArgumentException("파일 변환 중 에러가 발생");
        }

        return fileName;
    }

    private String createFileName(String originalFilename) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFilename));
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public void deleteImage(String url){
        awsS3Service.deleteFile(getFileName(url));
    }

    private String getFileName(String url) {
        try {
            return url.substring(url.lastIndexOf("."));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일 입니다.");
        }
    }


}
