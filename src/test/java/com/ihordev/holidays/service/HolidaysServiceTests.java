package com.ihordev.holidays.service;

import com.ihordev.holidays.repository.HolidaysRepository;
import com.ihordev.holidays.service.impl.HolidaysServiceImpl;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.Assert;
import org.junit.Test;

public class HolidaysServiceTests implements WithBDDMockito {

    @Test
    public void shouldGetHolidaysBetweenTwoDatesCorrectly() {
        int mockHolidaysRepositoryReturnedHolidays = 10;
        int expectedHolidaysBetween = 66 + mockHolidaysRepositoryReturnedHolidays;
        String startDate = "2016.12.09";
        String endDate = "2017.07.26";

        HolidaysRepository holidaysRepository = mock(HolidaysRepository.class);
        given(holidaysRepository.countHolidaysBetween(eq(startDate), eq(endDate)))
                .willReturn(mockHolidaysRepositoryReturnedHolidays);

        HolidaysServiceImpl holidaysService = new HolidaysServiceImpl();
        holidaysService.setHolidaysRepository(holidaysRepository);

        int actualHolidaysBetween = holidaysService.getHolidaysBetween(startDate, endDate);

        Assert.assertEquals(expectedHolidaysBetween, actualHolidaysBetween);
    }
}
