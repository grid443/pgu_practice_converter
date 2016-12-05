package ru.pgu.practice.csv_to_doc.controller;

import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.pgu.practice.csv_to_doc.model.Response;
import ru.pgu.practice.csv_to_doc.service.ConverterService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/", produces = APPLICATION_JSON_VALUE)
public class ConverterController {

    private final ConverterService converterService;

    private final TaskExecutor executor;

    public ConverterController(ConverterService converterService, TaskExecutor executor) {
        this.converterService = converterService;
        this.executor = executor;
    }

    /**
     * TODO write javaDoc
     * @return
     */
    @RequestMapping(value = "/test", method = {GET, POST})
    public String test() {
        return "success";
    }


    /**
     * TODO write javaDoc
     * @param csvFile
     */
    @RequestMapping(value = "/convert", consumes = "multipart/form-data", method = {GET, POST})
    public void convertCsv(@RequestParam("file") MultipartFile csvFile) {
        executor.execute(() -> converterService.start(csvFile));
    }

    /**
     * TODO write javaDoc
     * @return
     */
    @RequestMapping(value = "/check", method = {GET, POST})
    public Response checkResult() {
        return converterService.checkResult();
    }
}
