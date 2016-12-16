package ru.pgu.practice.csv_to_doc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import ru.pgu.practice.csv_to_doc.model.DataRow;
import ru.pgu.practice.csv_to_doc.model.Response;
import ru.pgu.practice.csv_to_doc.model.Sex;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConverterService {
    private final Logger log = LoggerFactory.getLogger(ConverterService.class);

    private static final String ROOT_DIR = System.getProperties().getProperty("user.dir");
    private static final String DATA_DIR = "data";
    private static final String TEMPLATE_DIR = "template";
    private static final String TEMPLATE_FILENAME = "template.xlsx";
    private ArrayList<DataRow> aRow = new ArrayList<DataRow>();

    /**
     * TODO write javaDoc
     * @param file
     */
    public void start(MultipartFile file) {
        if (file == null) {
            log.error("FILE IS NULL");
            return;
        }

        try {
            File csvFile = toFile(file);
            Files.lines(csvFile.toPath(), StandardCharsets.UTF_8)
                    .filter(line -> {
                       // line != "fio;age;sex;salary"; //NEVER COMPARE STRINGS USING == OR != !!!
                        return !Objects.equals(line, "fio;age;sex;salary");
                    })
                    .map(line -> {
                        String[] parts;
                        parts = line.split(";");
                        int Age = Integer.valueOf(parts[1]);
                        Sex sex = Sex.valueOf(parts[2]);
                        BigDecimal Salary = new BigDecimal(parts[3]);
                        DataRow row = new DataRow(parts[0], Age, sex, Salary);
                        return row;
                    })
                    .forEach((DataRow line) -> {

                    } );

            //TODO convert csv File using Apache POI
            //TODO write xlsx files by 10 lines.
            //For example, if you have 26 lines in source file,
            //you should create 2 xlsx files with 10 lines and 1 xlsx file with 6 lines
        } catch (Exception e) {
            log.error("FILE CONVERTING ERROR: \n", e);
        }
    }

    /**
     * TODO write javaDoc
     * @return
     */
    public Response checkResult() {
        Response response = new Response();
        return response;
    }

    private void writeXlsFile(List<DataRow> rows) {
        Path templateFile = Paths.get(ROOT_DIR, TEMPLATE_DIR, TEMPLATE_FILENAME);

        //TODO write rows to xlsx file using Apache POI
    }

    private File toFile(MultipartFile multipartFile) throws IOException {
        Path dataDir = Paths.get(ROOT_DIR, DATA_DIR);
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        File result = Paths.get(ROOT_DIR, DATA_DIR, multipartFile.getOriginalFilename()).toFile();
        multipartFile.transferTo(result);
        return result;
    }
}
