package com.project.bll.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateTimeConverter {

    /**
     * Heavily inspired by Tawfik
     */

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATE_PATTERN_GUI = "yyyy-MM-dd HH:mm";

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    private static final DateTimeFormatter DATE_FORMATTER_GUI =
            DateTimeFormatter.ofPattern(DATE_PATTERN_GUI);

    public static String formatDateTime(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER_GUI.format(date);
    }


    public static LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return DATE_FORMATTER_GUI.parse(dateTimeString, LocalDateTime::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    public static LocalDate parseDate(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString,LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateTimeConverter.parseDateTime(dateString) != null;
    }

    public static Date convertToDate(LocalDate dateToConvert){
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date convertToDateTime(LocalDateTime dateToConvert){
        Date date = Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public static Date parse_convertDateTime(String dateTimeString){
        LocalDateTime ldt = parseDateTime(dateTimeString);
        return convertToDateTime(ldt);
    }

    public static Date parse_convertDate(String dateString){
        LocalDate ld = parseDate(dateString);
        return convertToDate(ld);
    }
}
