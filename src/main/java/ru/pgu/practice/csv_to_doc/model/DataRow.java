package ru.pgu.practice.csv_to_doc.model;

import java.math.BigDecimal;

/**
 * TODO write javaDoc
 */
public class DataRow {

    private final String FIO;
    private final int age;
    private final Sex sex;
    private final BigDecimal salary;

    public DataRow(String F, int A, Sex S, BigDecimal s) {
        FIO = F;
        age = A;
        sex = S;
        salary = s;
    }

    public String getFIO() {
        return FIO;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}
