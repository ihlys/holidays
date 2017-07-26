package com.ihordev.holidays.core.util;

import org.junit.Assert;
import org.junit.Test;

import static com.ihordev.holidays.core.util.DateUtils.*;

public class DateUtilsTests {

    @Test
    public void shouldCountLeapYearsCorrectly() {
        int expectedLeapYearsCount1 = 0;
        int expectedLeapYearsCount2 = 53;
        int expectedLeapYearsCount3 = 59;
        int year1 = 1000;
        int year2 = 1800;
        int year3 = 1824;

        int actualLeapYearsCount1 = DateUtils.countLeapYears(year1);
        int actualLeapYearsCount2 = DateUtils.countLeapYears(year2);
        int actualLeapYearsCount3 = DateUtils.countLeapYears(year3);

        Assert.assertEquals(expectedLeapYearsCount1, actualLeapYearsCount1);
        Assert.assertEquals(expectedLeapYearsCount2, actualLeapYearsCount2);
        Assert.assertEquals(expectedLeapYearsCount3, actualLeapYearsCount3);
    }

    @Test
    public void shouldDetermineIfYearIsLeapYearCorrectly() {
        int year1 = 1000;
        int year2 = 1600;
        int year3 = 1800;
        int year4 = 1824;

        boolean actualIfYearIsLeapYear1 = DateUtils.isLeapYear(year1);
        boolean actualIfYearIsLeapYear2 = DateUtils.isLeapYear(year2);
        boolean actualIfYearIsLeapYear3 = DateUtils.isLeapYear(year3);
        boolean actualIfYearIsLeapYear4 = DateUtils.isLeapYear(year4);

        Assert.assertFalse(actualIfYearIsLeapYear1);
        Assert.assertTrue(actualIfYearIsLeapYear2);
        Assert.assertFalse(actualIfYearIsLeapYear3);
        Assert.assertTrue(actualIfYearIsLeapYear4);
    }

    @Test
    public void shouldCountYearsDaysFromAnchorCorrectly() {
        int expectedYearsDaysFromAnchor = 1826;

        int year = 1905;

        int actualYearsDaysFromAnchor = countYearsDaysFromAnchor(year);

        Assert.assertEquals(expectedYearsDaysFromAnchor, actualYearsDaysFromAnchor);
    }

    @Test
    public void shouldCountMonthsDaysFromAnchorCorrectly() {
        int expectedMonthsDaysFromAnchor1 = 31 + 28;
        int expectedMonthsDaysFromAnchor2 = 31 + 29;

        int actualMonthsDaysFromAnchor1 = countMonthsDaysFromAnchor(3, false);
        int actualMonthsDaysFromAnchor2 = countMonthsDaysFromAnchor(3, true);

        Assert.assertEquals(expectedMonthsDaysFromAnchor1, actualMonthsDaysFromAnchor1);
        Assert.assertEquals(expectedMonthsDaysFromAnchor2, actualMonthsDaysFromAnchor2);
    }

    @Test
    public void shouldCountDaysFromAnchorCorrectly() {
        int expectedDaysFromAnchor = 1534; // 365 * 4 + 31 + 29 + (15 - 1)

        int year = 1904;
        int month = 3;
        int daysOfMonth = 15;

        int actualDaysFromAnchor = countDaysFromAnchor(year, month, daysOfMonth);

        Assert.assertEquals(expectedDaysFromAnchor, actualDaysFromAnchor);
    }

    @Test
    public void shouldCountWeekDayCorrectly() {
        int expectedWeekDay = 3;
        Date date = Date.of(2017, 7, 26);

        int actualWeekDay = countWeekDay(date.getDaysFromAnchor());

        Assert.assertEquals(expectedWeekDay, actualWeekDay);
    }
}
