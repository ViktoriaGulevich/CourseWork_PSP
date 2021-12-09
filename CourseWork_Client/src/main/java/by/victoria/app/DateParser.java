package by.victoria.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateParser {
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date stringToDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Can't parse " + date + " to java.util.Date", e);
        }
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
    }

    public static Date localDateAndStringTimeToDate(LocalDate date, String time) {
        String datetime = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " + time;
        return stringToDate(datetime);
    }
}
