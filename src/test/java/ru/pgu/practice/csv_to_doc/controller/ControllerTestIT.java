package ru.pgu.practice.csv_to_doc.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import ru.pgu.practice.csv_to_doc.Application;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class ControllerTestIT {

    @Autowired
    private ConverterController controller;

    @Test
    public void dependencies() throws Exception {
        controller.test();
    }

    @Test
    public void converterTest() {
        //TODO Read test.csv file
        //TODO convert it to MultipartFile
        //TODO call ConverterService.start(file)
    }

    //TODO
    private MultipartFile toMultipartFile(File file) {
        return null;
    }
}
