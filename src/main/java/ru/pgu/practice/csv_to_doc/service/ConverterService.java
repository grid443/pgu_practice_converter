package ru.pgu.practice.csv_to_doc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConverterService {
    private final Logger log = LoggerFactory.getLogger(ConverterService.class);

    private static final String ROT_DIR = System.getProperties().getProperty("user.dir");
    private static final String DATA_DIR = "data";
    private static final String TEMPLATE_DIR = "template";
    private static final String TEMPLATE_FILENAME = "template.xlsx";

    public void start(MultipartFile file) {
        if (file == null) {
            log.error("FILE IS NULL");
            return;
        }

        try {
            File csvFile = toFile(file);
            //TODO convert csv File using Apache POI
        } catch (Exception e) {
            log.error("FILE CONVERTING ERROR: \n", e);
        }
    }

    private File toFile(MultipartFile multipartFile) throws IOException {
        Path dataDir = Paths.get(ROT_DIR, DATA_DIR);
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        File result = Paths.get(ROT_DIR, DATA_DIR, multipartFile.getOriginalFilename()).toFile();
        multipartFile.transferTo(result);
        return result;
    }
}
