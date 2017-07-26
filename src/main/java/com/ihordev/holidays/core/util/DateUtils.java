package com.ihordev.holidays.core.util;

public final class DateUtils {

    private static final int GREGORIAN_CALENDAR_YEAR = 1582;

    private static final int ANCHOR_YEAR = 1900;
    private static final int ANCHOR_LEAP_YEARS = countLeapYears(ANCHOR_YEAR);
    private static final int ANCHOR_MONTH = 1;
    private static final int ANCHOR_DAY_OF_MONTH = 1;
    private static final int ANCHOR_WEEK_DAY = 1;

    public static final int[] MONTHS_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static final int DAYS_IN_YEAR = 365;
    public static final int DAYS_IN_WEEK = 7;

    private DateUtils() {
        throw new AssertionError("DateUtils cannot be instantiated.");
    }

    public static int getDaysBetween(Date startDate, Date endDate) {
        return endDate.getDaysFromAnchor() - startDate.getDaysFromAnchor();
    }

    public static int countLeapYears(int year) {
        int leapYears = countLeapYearsRaw(year) - countLeapYearsRaw(GREGORIAN_CALENDAR_YEAR);
        return leapYears < 0 ? 0 : leapYears;
    }

    private static int countLeapYearsRaw(int year) {
        return year / 4 - year / 100 + year / 400;
    }

    public static boolean isLeapYear(int year) {
        return (year >= GREGORIAN_CALENDAR_YEAR) && (year % 100 == 0 ? year % 400 == 0 : year % 4 == 0);
    }

    static int countDaysFromAnchor(int year, int month, int dayOfMonth) {
        int days = 0;
        days += countYearsDaysFromAnchor(year);
        days += countMonthsDaysFromAnchor(month, isLeapYear(year));
        days += dayOfMonth - ANCHOR_DAY_OF_MONTH;
        return days;
    }

    static int countYearsDaysFromAnchor(int year) {
        return Math.abs(year - ANCHOR_YEAR) * DAYS_IN_YEAR +
                Math.abs(countLeapYears(year - 1) - ANCHOR_LEAP_YEARS);
    }

    static int countMonthsDaysFromAnchor(int month, boolean isLeapYear) {
        int days = 0;
        for (int i = 0; i < month - 1; i++) { // -1 because last month is not full and it's days are calc. separately
            days += MONTHS_DAYS[i];
            if (i + 1 == 2 && isLeapYear) days++; // february days in leap year
        }
        return days;
    }

    static int countWeekDay(int daysFromAnchor) {
        int weekDay = (daysFromAnchor + 1) % 7; // +1 because anchor date is Monday 1
        return (weekDay == 0) ? 7 : weekDay;
    }
}
