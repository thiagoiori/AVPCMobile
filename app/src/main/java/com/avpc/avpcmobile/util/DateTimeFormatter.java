package com.avpc.avpcmobile.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatter {
    public static String formatDate(long dateInLong) {
        return DateFormat.getDateInstance().format(new Date(dateInLong));
    }
}
