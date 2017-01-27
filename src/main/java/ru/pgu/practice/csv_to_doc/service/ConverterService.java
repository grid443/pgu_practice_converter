package ru.pgu.practice.csv_to_doc.service;

import org.springframework.web.multipart.MultipartFile;
import ru.pgu.practice.csv_to_doc.model.Response;

/**
 * Convert csv file to xls file
 */
public interface ConverterService {
    /**
     * Start converting text file into xlsx
     * To get result files list call {@link ru.pgu.practice.csv_to_doc.service.impl.ConverterServiceImpl#checkResult} method
     * with the same operationId
     *
     * @param file source file
     */
    void start(MultipartFile file, String operationId);

    /**
     * Check operation result
     * Returns result files list or
     * operation state if it not
     * completed yet
     */
    Response checkResult(String operationId);
}
