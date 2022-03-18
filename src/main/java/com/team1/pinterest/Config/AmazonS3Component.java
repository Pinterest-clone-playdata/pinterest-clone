package com.team1.pinterest.Config;

import com.team1.pinterest.Service.YamlPropertySourceFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties(prefix = "yaml")
@PropertySource(value = "classpath:aws.yml", factory = YamlPropertySourceFactory.class)
public class AmazonS3Component {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
}
