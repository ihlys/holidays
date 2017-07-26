package com.ihordev.holidays.core.util;

import static com.ihordev.holidays.core.util.DateUtils.*;
import static java.lang.String.format;

public class Date {

    private static final int GREGORIAN_CALENDAR_YEAR = 1582;

    private int year;
    private int month;
    private int dayOfMonth;

    private int daysFromAnchor;
    private int weekDay;

    private Date(int year, int month, int dayOfMonth) {
        this.year = checkYear(year);
        this.month = checkMonth(month);
        this.dayOfMonth = checkDayOfMonth(dayOfMonth, month, isLeapYear(year));
        this.daysFromAnchor = countDaysFromAnchor(this.year, this.month, this.dayOfMonth);
        this.weekDay = countWeekDay(this.daysFromAnchor);
    }

    public static Date of(String date) {
        String[] dateValues = date.split("\\.");
        return new Date(Integer.parseInt(dateValues[0]), Integer.parseInt(dateValues[1]),
                Integer.parseInt(dateValues[2]));
    }

    public static Date of(int year, int month, int dayOfMonth) {
        return new Date(year, month, dayOfMonth);
    }

    private int checkYear(int year) {
        if (year < GREGORIAN_CALENDAR_YEAR) {
            String errMsg = format("year must be equal or greater than %s.", GREGORIAN_CALENDAR_YEAR);
            throw new IllegalArgumentException(errMsg);
        }
        return year;
    }

    private int checkMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("month must be in [1..12]");
        }
        return month;
    }

    private int checkDayOfMonth(int dayOfMonth, int month, boolean isLeapYear) {
        int monthDays = (month == 2 && isLeapYear) ? MONTHS_DAYS[month - 1] + 1 : MONTHS_DAYS[month - 1];
        if (dayOfMonth < 0 || dayOfMonth > monthDays) {
            String errMsg = format("dayOfMonth must be in [1..%s]", monthDays);
            throw new IllegalArgumentException(errMsg);
        }
        return dayOfMonth;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getWeekDay() {
        return weekDay;
    }

    int getDaysFromAnchor() {
        return daysFromAnchor;
    }
}
