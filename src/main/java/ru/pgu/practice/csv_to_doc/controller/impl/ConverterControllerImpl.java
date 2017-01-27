package ru.pgu.practice.csv_to_doc.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.pgu.practice.csv_to_doc.controller.ConverterController;
import ru.pgu.practice.csv_to_doc.model.Response;
import ru.pgu.practice.csv_to_doc.service.ConverterService;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/", produces = APPLICATION_JSON_VALUE)
public class ConverterControllerImpl implements ConverterController {

    private final ConverterService converterService;

    private final TaskExecutor executor;

    @Autowired
    public ConverterControllerImpl(ConverterService converterService, TaskExecutor executor) {
        this.converterService = converterService;
        this.executor = executor;
    }

    @Override
    @RequestMapping(value = "/ping", method = {GET, POST})
    public String ping() {
        return "pong";
    }

    @Override
    @RequestMapping(value = "/convert", consumes = "multipart/form-data", method = {GET, POST})
    public String convertCsv(@RequestParam("file") MultipartFile csvFile) {
        String operationId = UUID.randomUUID().toString();
        executor.execute(() -> converterService.start(csvFile, operationId));
        return operationId;
    }

    @Override
    @RequestMapping(value = "/check", method = {GET, POST})
    public Response checkResult(String operationId) {
        return converterService.checkResult(operationId);
    }
}
