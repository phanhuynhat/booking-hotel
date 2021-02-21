package com.nhat.demo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class test {
    public static void main(String[] args) {
        long between = ChronoUnit.DAYS.between(LocalDate.parse("2020-02-20"), LocalDate.parse("2020-02-24"));
        System.out.println(between);

    }
}
