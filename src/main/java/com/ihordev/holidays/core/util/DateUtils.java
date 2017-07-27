package com.ihordev.holidays.core.util;

/**
 * <p>A class that provides utilities for date operations. All it's methods
 * are based on anchor date which is 1 january 1900 and fact that this day
 * is monday.
 */
public final class DateUtils {

    private static final int GREGORIAN_CALENDAR_YEAR = 1582;

    private static final int ANCHOR_YEAR = 1900;
    private static final int ANCHOR_LEAP_YEARS = countLeapYears(ANCHOR_YEAR);
    private static final int ANCHOR_DAY_OF_MONTH = 1;

    public static final int[] MONTHS_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static final int DAYS_IN_YEAR = 365;
    public static final int DAYS_IN_WEEK = 7;

    private DateUtils() {
        throw new AssertionError("DateUtils cannot be instantiated.");
    }

    /**
     * Counts days between two dates.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return number of days between two dates
     */
    public static int getDaysBetween(Date startDate, Date endDate) {
        return endDate.getDaysFromAnchor() - startDate.getDaysFromAnchor();
    }

    /**
     * Counts leap years from start of gregorian calendar (1582 year) to specified
     * year.
     *
     * @see #isLeapYear(int)
     *
     * @param year the year for which leap years must be counted
     * @return leap years for specified year
     */
    public static int countLeapYears(int year) {
        int leapYears = countLeapYearsRaw(year) - countLeapYearsRaw(GREGORIAN_CALENDAR_YEAR);
        return leapYears < 0 ? 0 : leapYears;
    }

    private static int countLeapYearsRaw(int year) {
        return year / 4 - year / 100 + year / 400;
    }

    /**
     * Determines if specified year is a leap year. A leap year is a year after 1582 year
     * (gregorian calendar start) with such properties:
     * <pre>
     * if (year is not divisible by 4) then (it is a common year)
     * else if (year is not divisible by 100) then (it is a leap year)
     * else if (year is not divisible by 400) then (it is a common year)
     * else (it is a leap year)
     * </pre>
     *
     * @param year the year to check if it is a leap year
     * @return if specified year is a leap year
     */
    public static boolean isLeapYear(int year) {
        return (year >= GREGORIAN_CALENDAR_YEAR) && (year % 100 == 0 ? year % 400 == 0 : year % 4 == 0);
    }

    /**
     * Counts number of days from anchor date. It firstly counts days between specified
     * year and year of the anchor date, then days between specified month and month
     * of the anchor date and days between specified day of month and day of month of
     * the anchor date. Is uses {@link #countYearsDaysFromAnchor(int)},
     * {@link #countMonthsDaysFromAnchor} and {@link #countDaysOfMonthFromAnchor(int)}
     * methods internally. Specified date can be before anchor date (result number of
     * days is a modulus).
     *
     * @param year the year of date
     * @param month the month of date
     * @param dayOfMonth the day of month of date
     * @return number of days from anchor date
     */
    static int countDaysFromAnchor(int year, int month, int dayOfMonth) {
        int days = 0;
        days += countYearsDaysFromAnchor(year);
        days += countMonthsDaysFromAnchor(month, isLeapYear(year));
        days += countDaysOfMonthFromAnchor(dayOfMonth);
        return days;
    }

    /**
     * Counts number of days between specified year and year of the anchor date.
     *
     * @param year the year for which days must be counted
     * @return number of days
     */
    static int countYearsDaysFromAnchor(int year) {
        return Math.abs(year - ANCHOR_YEAR) * DAYS_IN_YEAR +
                Math.abs(countLeapYears(year - 1) - ANCHOR_LEAP_YEARS);
    }

    /**
     * Counts number of days between specified month and month of the anchor date,
     * excluding last month. To calculate days for last month use
     * {@link #countDaysOfMonthFromAnchor(int)}.
     *
     * @param month the month for which days must be counted
     * @param isLeapYear if year is a leap year than there would be 29 days
     *                   counted in february
     * @return number of days
     */
    static int countMonthsDaysFromAnchor(int month, boolean isLeapYear) {
        int days = 0;
        for (int i = 0; i < month - 1; i++) { // -1 because last month is not full and it's days are calc. separately
            days += MONTHS_DAYS[i];
            if (i + 1 == 2 && isLeapYear) days++; // february days in leap year
        }
        return days;
    }

    /**
     * Counts number of days between specified day of month and day of month of
     * the anchor date.
     *
     * @param dayOfMonth the day of month
     * @return number of days
     */

    static int countDaysOfMonthFromAnchor(int dayOfMonth) {
        return dayOfMonth - ANCHOR_DAY_OF_MONTH;
    }

    /**
     * Counts week day based on days from anchor date;
     *
     * @param daysFromAnchor the number of days from anchor date
     * @return week day
     */
    static int countWeekDay(int daysFromAnchor) {
        int weekDay = (daysFromAnchor + 1) % 7; // +1 because anchor date is Monday 1
        return (weekDay == 0) ? 7 : weekDay;
    }
}
