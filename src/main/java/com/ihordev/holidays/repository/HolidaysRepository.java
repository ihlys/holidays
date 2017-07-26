package com.ihordev.holidays.repository;

public interface HolidaysRepository {

    int countHolidaysBetween(String startDate, String endDate);
}
