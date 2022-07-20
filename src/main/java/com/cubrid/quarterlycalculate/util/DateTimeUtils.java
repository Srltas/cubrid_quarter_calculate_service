package com.cubrid.quarterlycalculate.util;

import java.sql.Date;
import java.time.LocalDate;

public class DateTimeUtils {

    public static Date timestampOf(LocalDate time) {
        return time == null ? null : Date.valueOf(time);
    }

    public static LocalDate dateTimeOf(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
