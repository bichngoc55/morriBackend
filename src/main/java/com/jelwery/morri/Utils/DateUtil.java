package com.jelwery.morri.Utils;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateUtil {
 private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static Date parseDate(String dateString) throws Exception {
        return dateFormat.parse(dateString);
    }
}
