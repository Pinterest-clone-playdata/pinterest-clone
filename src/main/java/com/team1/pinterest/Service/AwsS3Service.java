package com.team1.pinterest.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team1.pinterest.Config.AmazonS3Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class AwsS3Service {

    private final AmazonS3Component amazonS3Component;
    private final AmazonS3 amazonS3;

    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName){
        amazonS3.putObject(new PutObjectRequest(amazonS3Component.getBucket(), fileName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicReadWrite));
    }

    public void deleteFile(String fileName){
        amazonS3.deleteObject(new DeleteObjectRequest(amazonS3Component.getBucket(), fileName));
    }

    public String getFileUrl(String fileName){
        return amazonS3.getUrl(amazonS3Component.getBucket(),fileName).toString();
    }

    public String getFileName(String url){
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
