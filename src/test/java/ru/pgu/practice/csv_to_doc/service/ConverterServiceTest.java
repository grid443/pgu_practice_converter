package ru.pgu.practice.csv_to_doc.service;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.pgu.practice.csv_to_doc.service.impl.ConverterServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

public class ConverterServiceTest {

    /**
     * test for empty file
     * checks whether there is a file in result folder
     */
    @Test
    public void emptyFileTest() throws Exception {
        URL csvLocation = this.getClass().getClassLoader().getResource("emptyFile.csv");
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

        ConverterServiceImpl converter = new ConverterServiceImpl();
        String operationId = UUID.randomUUID().toString();
        converter.start(multipartFile, operationId);

        File resultDir = Paths.get(
                privateStringFieldValue(converter, "ROOT_DIR"),
                privateStringFieldValue(converter, "RESULT_DIR"),
                operationId
        ).toFile();

        Assert.assertFalse(resultDir.exists());

        clearDataFiles(converter, operationId);
    }

    /**
     * test for file with single line
     * checks whether there is a file in result folder
     * checks the file itself
     */
    @Test
    public void oneLineFileTest() throws Exception {
        URL csvLocation = this.getClass().getClassLoader().getResource("oneLine.csv");
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

        ConverterServiceImpl converter = new ConverterServiceImpl();
        String operationId = UUID.randomUUID().toString();
        converter.start(multipartFile, operationId);

        File resultDir = Paths.get(
                privateStringFieldValue(converter, "ROOT_DIR"),
                privateStringFieldValue(converter, "RESULT_DIR"),
                operationId
        ).toFile();

        File[] resultFiles = resultDir.listFiles();
        Assert.assertNotNull(resultFiles);
        Assert.assertEquals(1, resultFiles.length);
        File file = resultFiles[0];

        HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet myExcelSheet = myExcelBook.getSheet("CSV Convert");
        HSSFRow row = myExcelSheet.getRow(0);
        Assert.assertEquals("Иван Пупкин", row.getCell(0).getStringCellValue());

        Assert.assertEquals("23", row.getCell(1).getStringCellValue());

        Assert.assertEquals("Мужской", row.getCell(2).getStringCellValue());

        Assert.assertEquals("12000", row.getCell(3).getStringCellValue());
        HSSFRow rowTwo = myExcelSheet.getRow(1);

        Assert.assertEquals("Всего:", rowTwo.getCell(1).getStringCellValue());
        Assert.assertEquals("12000", rowTwo.getCell(3).getStringCellValue());

        Assert.assertTrue(file.delete());
        Assert.assertTrue(resultDir.delete());

        Assert.assertFalse(file.exists());
        Assert.assertFalse(resultDir.exists());

        clearDataFiles(converter, operationId);
    }

    /**
     * test for file with ten lines
     * checks whether there is a file in result folder
     * checks first row, last row and "Total" row in file
     */
    @Test
    public void tenLinesFileTest() throws Exception {
        URL csvLocation = this.getClass().getClassLoader().getResource("tenLines.csv");
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

        ConverterServiceImpl converter = new ConverterServiceImpl();
        String operationId = UUID.randomUUID().toString();
        converter.start(multipartFile, operationId);

        File resultDir = Paths.get(
                privateStringFieldValue(converter, "ROOT_DIR"),
                privateStringFieldValue(converter, "RESULT_DIR"),
                operationId
        ).toFile();

        File[] resultFiles = resultDir.listFiles();
        Assert.assertNotNull(resultFiles);
        Assert.assertEquals(1, resultFiles.length);
        File file = resultFiles[0];

        HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet myExcelSheet = myExcelBook.getSheet("CSV Convert");
        HSSFRow row = myExcelSheet.getRow(0);
        Assert.assertEquals("Иван Иванович", row.getCell(0).getStringCellValue());

        Assert.assertEquals("18", row.getCell(1).getStringCellValue());

        Assert.assertEquals("Мужской", row.getCell(2).getStringCellValue());

        Assert.assertEquals("12800", row.getCell(3).getStringCellValue());

        HSSFRow tenthRow = myExcelSheet.getRow(9);
        Assert.assertEquals("Надежда Суркова", tenthRow.getCell(0).getStringCellValue());

        Assert.assertEquals("21", tenthRow.getCell(1).getStringCellValue());

        Assert.assertEquals("Женский", tenthRow.getCell(2).getStringCellValue());

        Assert.assertEquals("15000", tenthRow.getCell(3).getStringCellValue());

        HSSFRow totalRow = myExcelSheet.getRow(10);

        Assert.assertEquals("Всего:", totalRow.getCell(1).getStringCellValue());
        Assert.assertEquals("203000", totalRow.getCell(3).getStringCellValue());

        Assert.assertTrue(file.delete());
        Assert.assertTrue(resultDir.delete());

        clearDataFiles(converter, operationId);
    }

    /**
     * test for file with eleven lines
     * checks whether two files are creating in result folder
     * checks first row, last row and "Total" row in firs file
     * and checks first row and "Total" row in second file
     */
    @Test
    public void elevenLinesFileTest() throws Exception {
        URL csvLocation = this.getClass().getClassLoader().getResource("elevnLines.csv");
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

        ConverterServiceImpl converter = new ConverterServiceImpl();
        String operationId = UUID.randomUUID().toString();
        converter.start(multipartFile, operationId);

        File resultDir = Paths.get(
                privateStringFieldValue(converter, "ROOT_DIR"),
                privateStringFieldValue(converter, "RESULT_DIR"),
                operationId
        ).toFile();

        File[] resultFiles = resultDir.listFiles();
        Assert.assertNotNull(resultFiles);
        Assert.assertEquals(2, resultFiles.length);
        Arrays.sort(resultFiles);
        File firstFile = resultFiles[0];
        File secondFile = resultFiles[1];

        HSSFWorkbook firstExcelBook = new HSSFWorkbook(new FileInputStream(firstFile));
        HSSFSheet firstExcelSheet = firstExcelBook.getSheet("CSV Convert");
        HSSFRow firstFileRow = firstExcelSheet.getRow(0);
        Assert.assertEquals("Иван Иванович", firstFileRow.getCell(0).getStringCellValue());

        Assert.assertEquals("18", firstFileRow.getCell(1).getStringCellValue());

        Assert.assertEquals("Мужской", firstFileRow.getCell(2).getStringCellValue());

        Assert.assertEquals("12800", firstFileRow.getCell(3).getStringCellValue());

        HSSFRow tenthRow = firstExcelSheet.getRow(9);
        Assert.assertEquals("Надежда Суркова", tenthRow.getCell(0).getStringCellValue());

        Assert.assertEquals("21", tenthRow.getCell(1).getStringCellValue());

        Assert.assertEquals("Женский", tenthRow.getCell(2).getStringCellValue());

        Assert.assertEquals("15000", tenthRow.getCell(3).getStringCellValue());

        HSSFRow firstFileTotalRow = firstExcelSheet.getRow(10);
        Assert.assertEquals("Всего:", firstFileTotalRow.getCell(1).getStringCellValue());

        Assert.assertEquals("203000", firstFileTotalRow.getCell(3).getStringCellValue());

        HSSFWorkbook secExcelBook = new HSSFWorkbook(new FileInputStream(secondFile));
        HSSFSheet secExcelSheet = secExcelBook.getSheet("CSV Convert");
        HSSFRow secFileRow = secExcelSheet.getRow(0);
        Assert.assertEquals("Иван Моржов", secFileRow.getCell(0).getStringCellValue());

        Assert.assertEquals("25", secFileRow.getCell(1).getStringCellValue());

        Assert.assertEquals("Мужской", secFileRow.getCell(2).getStringCellValue());

        Assert.assertEquals("13000", secFileRow.getCell(3).getStringCellValue());

        HSSFRow secTotalRow = secExcelSheet.getRow(1);
        Assert.assertEquals("Всего:", secTotalRow.getCell(1).getStringCellValue());

        Assert.assertEquals("13000", secTotalRow.getCell(3).getStringCellValue());

        Assert.assertTrue(firstFile.delete());
        Assert.assertTrue(secondFile.delete());
        Assert.assertTrue(resultDir.delete());

        clearDataFiles(converter, operationId);
    }

    //Access to object's private fields
    private String privateStringFieldValue(Object object, String fieldName) {
        try {
            Field rootDirField = ConverterServiceImpl.class.getDeclaredField(fieldName);
            rootDirField.setAccessible(true);
            return (String) rootDirField.get(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void clearDataFiles(ConverterServiceImpl converter, String operationId) {
        File dataDir = Paths.get(
                privateStringFieldValue(converter, "ROOT_DIR"),
                privateStringFieldValue(converter, "DATA_DIR"),
                operationId
        ).toFile();

        File[] dataFiles = dataDir.listFiles();
        Assert.assertNotNull(dataFiles);
        for (File dataFile : dataFiles) {
            Assert.assertTrue(dataFile.delete());
        }
        Assert.assertTrue(dataDir.delete());
    }
}
