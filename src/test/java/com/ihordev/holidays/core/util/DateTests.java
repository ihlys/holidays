package com.ihordev.holidays.core.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DateTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowExceptionIfSpecifiedYearIsLessThanGregorianCalendarYear() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("year must be equal or greater than 1582.");
        Date.of(1000, 1, 1);
    }

    @Test
    public void shouldThrowExceptionIfSpecifiedMonthIsNotInRangeOf_1_12() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("month must be in [1..12]");
        Date.of(2017, 567, 26);
    }

    @Test
    public void shouldThrowExceptionIfSpecifiedDayOfMonthIsNotInRangeOfFebruary_notLeapYear() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("dayOfMonth must be in [1..28]");
        Date.of(2017, 2, 29);
    }

    @Test
    public void shouldThrowExceptionIfSpecifiedDayOfMonthIsNotInRangeOfFebruary_leapYear() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("dayOfMonth must be in [1..29]");
        Date.of(2016, 2, 30);
    }
}
