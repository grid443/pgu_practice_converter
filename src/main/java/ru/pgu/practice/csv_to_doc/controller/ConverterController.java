package ru.pgu.practice.csv_to_doc.controller;

import org.springframework.web.multipart.MultipartFile;
import ru.pgu.practice.csv_to_doc.model.Response;

public interface ConverterController {
    /**
     * Dummy controller
     * Just for testing
     */
    String ping();

    /**
     * Start converting text file to csv asynchronously
     * @param csvFile file to convert
     * @return        opration ID
     */
    String convertCsv(MultipartFile csvFile);

    /**
     * Check converting operation result
     * If the conversion has already finished,
     * the result contains files list
     * otherwise it contains operation state
     */
    Response checkResult(String operationId);
}
