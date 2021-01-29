package com.nhat.demo.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class test {
    public static void main(String[] args) {
        LocalDate parse = LocalDate.parse("26/10/1992", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(parse);
    }
}
