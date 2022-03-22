package com.project.gui.view;

import com.project.bll.util.DateTimeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Test {

    public static void main(String[] args) {
        LocalDate time = DateTimeConverter.parseDate("12/12/2021 12:12");
        LocalDateTime dateTime= DateTimeConverter.parseDateTime("2022-03-26 02:03");
        System.out.println(time + "\n" + dateTime);
    }
}
