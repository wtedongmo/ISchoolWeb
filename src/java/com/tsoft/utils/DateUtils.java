/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    public static final String DATETIME_FORMAT1 = "yyyy-MM-ddTHH:mm:ss";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";

    public DateUtils() {
    }

    public static String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDateTime(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Date getDateFromTime(LocalTime time) {
        return Date.from(time.atDate(LocalDate.of(1970, Month.JANUARY, 1)).atZone(ZoneId.of("UTC")).toInstant());
    }

    public static void main(String[] args) {
        System.out.println("Arthur --E--" + (new SimpleDateFormat("HH:mm")).format((Date)null));
        System.out.println(getCurrentDate());
        System.out.println(getCurrentTime());
        System.out.println(getCurrentDateTime());
    }

    public static void main1(String[] args) {
        LocalTime timee = LocalTime.of(7, 30);
        timee.plusHours(2L);
        LocalDateTime dateToConvert = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime dateToConvert2 = LocalDateTime.parse("2015-02-26T00:00:00.000Z", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Date convertToDate = Date.from(dateToConvert.atZone(ZoneId.of("UTC")).toInstant());
        Date convertToDate1 = Date.from(dateToConvert.plusMonths(6L).atZone(ZoneId.of("UTC")).toInstant());
        System.out.println(LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_DATE));
        System.out.println(convertToDate);
        System.out.println(convertToDate1);
        LocalDateTime ldt = LocalDateTime.ofInstant((new Date()).toInstant(), ZoneId.of("UTC"));
        System.out.println(ldt.toString());
        Date ss = new Date("Thu Jun 25 2015 01:00:00 GMT+0100 (WAT)");
        System.out.println(ss);
        Instant timestamp = (new Date()).toInstant();
        LocalDateTime date = LocalDateTime.ofInstant(timestamp, ZoneId.of("UTC"));
        System.out.println("Date = " + date);
        Instant time = Calendar.getInstance().toInstant();
        System.out.println(time);
        System.out.println(timestamp.toString());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
}
