package ru.pgu.practice.csv_to_doc.model;

import com.sun.org.apache.xpath.internal.operations.String;

import java.math.BigDecimal;
import ru.pgu.practice.csv_to_doc.model.Sex;

/**
 * TODO write javaDoc
 */
public class DataRow {

    private final String FIO;
    private final int age;
    private final Sex sex;
    private final BigDecimal salary;

    public String getFIO(){
        return FIO;
    }
    public int getAge(){
        return age;
    }
    public Sex getSex(){
        return sex;
    }
    public BigDecimal getSalary(){
        return salary;
    }

//Constructor
    DaraRow(String F, int A, Sex S, BigDecimal s) {
        FIO = F;
        age = A;
        sex = S;
        salary = s;
    }
}
