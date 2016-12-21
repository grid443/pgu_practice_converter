package ru.pgu.practice.csv_to_doc.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;


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
    }
}
