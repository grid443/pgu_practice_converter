package ru.pgu.practice.csv_to_doc.model;

import java.math.BigDecimal;

/**
 * class DataRow stores the row information about an employee
 */
public class DataRow {

    private final String fio;
    private final int age;
    private final Sex sex;
    private final BigDecimal salary;

    public DataRow(String fio, int age, Sex sex, BigDecimal salary) {
        this.fio = fio;
        this.age = age;
        this.sex = sex;
        this.salary = salary;
    }

    /**
     * full name employee
     * @return
     */
    public String getFio() {
        return fio;
    }

    /**
     * age employee
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * sex employee
     * @return
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * salary employee
     * @return
     */
    public BigDecimal getSalary() {
        return salary;
    }
}
