package ru.pgu.practice.csv_to_doc.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import ru.pgu.practice.csv_to_doc.model.DataRow;
import ru.pgu.practice.csv_to_doc.model.OperationState;
import ru.pgu.practice.csv_to_doc.model.Response;
import ru.pgu.practice.csv_to_doc.model.Sex;
import ru.pgu.practice.csv_to_doc.service.ConverterService;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConverterServiceImpl implements ConverterService {
    private final Logger log = LoggerFactory.getLogger(ConverterServiceImpl.class);

    private OperationState state;

    private static final String ROOT_DIR = System.getProperties().getProperty("user.dir");
    private static final String DATA_DIR = "data";
    private static final String RESULT_DIR = "result";
    private static final String RESULT_FILENAME_TEMPLATE = "result_%s.xlsx";
    private static final Pattern linePattern = Pattern
            .compile("\\p{IsAlphabetic}+( \\p{IsAlphabetic}+)?;\\d+;\\w;\\d+");

    private ArrayList<DataRow> rows = new ArrayList<>();


    @Override
    public void start(@NotNull MultipartFile file, @NotNull String operationId) {
        state = OperationState.PROCESS;
        try {
            File csvFile = toFile(file, operationId);

            Files.lines(csvFile.toPath(), StandardCharsets.UTF_8)
                    .filter(line -> {
                        Matcher lineMatcher = linePattern.matcher(line);
                        return lineMatcher.matches();
                    })
                    .map(line -> {
                        String[] parts = line.split(";");
                        int age = Integer.valueOf(parts[1]);
                        Sex sex = "m".equals(parts[2]) ? Sex.MALE : Sex.FEMALE;
                        BigDecimal salary = new BigDecimal(parts[3]);
                        return new DataRow(parts[0], age, sex, salary);
                    })
                    .forEach((DataRow row) -> {
                        rows.add(row);

                        // Write xlsx files by 10 lines
                        if (rows.size() == 10) {
                            writeXlsFile(Collections.unmodifiableList(rows), operationId);
                            rows.clear();
                        }
                    });

            // Writing the rest of the data
            if (rows.size() > 0) {
                writeXlsFile(Collections.unmodifiableList(rows), operationId);
                rows.clear();
            }

            state = OperationState.SUCCESS;
        } catch (Exception e) {
            log.error("FILE CONVERTING ERROR: \n", e);
            state = OperationState.ERROR;
        }
    }

    @Override
    public Response checkResult(@NotNull String operationId) {
        if (state != OperationState.SUCCESS) {
            return new Response(state);
        }

        Path resultDir = Paths.get(ROOT_DIR, RESULT_DIR, operationId);

        if (!Files.exists(resultDir)) {
            throw new IllegalStateException("Result directory not found");
        }

        List<String> files = new ArrayList<>();
        try {
            for (Path path : Files.newDirectoryStream(resultDir)) {
                files.add(path.toString());
            }

            return new Response(files);
        } catch (Exception e) {
            log.error("CHECK RESULT ERROR", e);
            return new Response(OperationState.ERROR);
        }
    }

    /*
     * Write xlsx file with data from 'rows'
     */
    private void writeXlsFile(List<DataRow> rows, String operationId) {
        String timestamp = String.valueOf(System.nanoTime());
        String resultFilename = String.format(RESULT_FILENAME_TEMPLATE, timestamp);
        Path resultDir = Paths.get(ROOT_DIR, RESULT_DIR, operationId);
        if (!Files.exists(resultDir)) {
            try {
                Files.createDirectories(resultDir);
            } catch (IOException e) {
                state = OperationState.ERROR;
                throw new IllegalStateException("Can't create result directory", e);
            }
        }

        File file = resultDir.resolve(resultFilename).toFile();

        try (Workbook book = new HSSFWorkbook();
             OutputStream os = new FileOutputStream(file)) {

            Sheet sheet = book.createSheet("CSV Convert");

            int rowNumber = 0;
            for (DataRow row : rows) {
                String fio = row.getFio();
                int age = row.getAge();
                String sex = row.getSex() == Sex.MALE ? "Мужской" : "Женский";
                BigDecimal salary = row.getSalary();
                Row sheetRow = sheet.createRow(rowNumber);
                Cell cellFio = sheetRow.createCell(0);
                cellFio.setCellValue(fio);

                Cell cellAge = sheetRow.createCell(1);
                cellAge.setCellValue(Integer.toString(age));

                Cell cellSex = sheetRow.createCell(2);
                cellSex.setCellValue(sex);

                Cell cellSalary = sheetRow.createCell(3);
                cellSalary.setCellValue(salary.toString());

                rowNumber++;
            }

            BigDecimal sum = rows.stream()
                    .map(DataRow::getSalary)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Row sumRow = sheet.createRow(rowNumber);
            Cell cellResult = sumRow.createCell(1);
            cellResult.setCellValue("Всего:");

            Cell cellSum = sumRow.createCell(3);
            cellSum.setCellValue(sum.toString());

            sheet.autoSizeColumn(0);
            book.write(os);
        } catch (FileNotFoundException e) {
            log.error("File {} not found", file);
        } catch (IOException e) {
            log.error("xlsx file writing error");
        }
    }

    private File toFile(MultipartFile multipartFile, String operationId) throws IOException {
        Path dataDir = Paths.get(ROOT_DIR, DATA_DIR, operationId);
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        File result = Paths.get(
                ROOT_DIR,
                DATA_DIR,
                operationId,
                multipartFile.getOriginalFilename()
        ).toFile();
        multipartFile.transferTo(result);
        return result;
    }
}
