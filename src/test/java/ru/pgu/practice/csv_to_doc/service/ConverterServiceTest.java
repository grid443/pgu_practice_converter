package ru.pgu.practice.csv_to_doc.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ConverterServiceTest {
    /**
     *
     */
    @Test
    public void testStart() throws Exception {
        URL csvLocation = this.getClass().getClassLoader().getResource("test.csv");
        Assert.assertNotNull(csvLocation);

        File csvFile = new File(csvLocation.toURI());
        byte[] content = Files.readAllBytes(csvFile.toPath());

        MultipartFile multipartFile = new MockMultipartFile(
            csvFile.getName(),
            csvFile.getName(),
            "text/plain",
            content
        );

        Assert.assertNotNull(multipartFile);

        ConverterService start = new ConverterService();
        start.start(multipartFile);


        Path resultDir = Paths.get(
            privateStringFieldValue(start, "ROOT_DIR"),
            privateStringFieldValue(start, "RESULT_DIR")
        );

    }

    private String privateStringFieldValue(Object object, String fieldName) {
        try {
            Field rootDirField = ConverterService.class.getDeclaredField(fieldName);
            rootDirField.setAccessible(true);
            return (String) rootDirField.get(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
