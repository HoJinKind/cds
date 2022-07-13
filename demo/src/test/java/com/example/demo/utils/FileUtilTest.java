package com.example.demo.utils;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class FileUtilTest {

    public static final String CONTENT_TYPE = "text/csv";
    private static final String TEST_RESOURCES = "src/test/resources/";

    @InjectMocks
    FileUtil fileUtil;


    @Test
    public void testOKFile() {
        String filePath = TEST_RESOURCES + "testOK.csv";
        Path path = Paths.get(filePath);
        String name = "testOK.csv";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name, name, CONTENT_TYPE, content);
        List<String> lineList = Lists.newArrayList();
        SuccessResult<Integer> status = FileUtil.readFile(result, lineList);
        System.out.println(lineList);
        Assert.assertTrue(status.getSuccess().equals(1));
    }
}