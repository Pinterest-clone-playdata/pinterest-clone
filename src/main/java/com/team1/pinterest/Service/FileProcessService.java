package com.team1.pinterest.Service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.team1.pinterest.Exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileProcessService {

    private final AwsS3Service awsS3Service;

    public String uploadImage(MultipartFile file) throws IOException{
        ContextFileCheck(file);
        checkImageSize(file);

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

    public void deleteImage(String fileName){
        awsS3Service.deleteFile(fileName);
    }

    private void checkImageSize(MultipartFile file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            if (width < 300 && height < 500){
                throw new IllegalStateException("파일 이미지가 너무 작습니다. 크기를 키워주세요");
            }

            System.out.println(String.format("width = %d height = %d", width, height));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createFileName(String originalFilename) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFilename));
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private void ContextFileCheck(MultipartFile file) {
        boolean FileContextType = false;
        final String[] PERMISSION_FILE_MIME_TYPE = {"image/gif", "image/jpeg", "image/png", "image/jpg"};

        for( int i = 0; i < PERMISSION_FILE_MIME_TYPE.length; i++ ) {
            if( PERMISSION_FILE_MIME_TYPE[i].equals(file.getContentType()) ) {
                FileContextType = true;
                break;
            }
        }
        if (!FileContextType){
            throw new IllegalStateException("gif,jpeg,jpg,png 파일만 업로드가 가능합니다.");
        }
    }
}
