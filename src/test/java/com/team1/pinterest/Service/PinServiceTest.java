package com.team1.pinterest.Service;

import io.findify.s3mock.S3Mock;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Profile("test")
@AutoConfigureMockMvc
@SpringBootTest
class PinServiceTest {

    @Autowired private MockMvc mvc;
    @Autowired FileProcessService fileProcessService;

    @Test
    public void TestMockMvc() throws IOException {

        MockMultipartFile image = new MockMultipartFile("image",
                "test.jpg",
                "image/jpg",
                new FileInputStream("C:\\Users\\skgod\\Desktop\\사진\\아 야스\\test.jpg"));

        assertThat(image.getContentType()).isEqualTo("image/jpg");
    }
}