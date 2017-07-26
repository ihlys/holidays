package com.ihordev.holidays.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
       DirtiesContextTestExecutionListener.class,
       TransactionDbUnitTestExecutionListener.class })
public class HolidaysRepositoryTests implements WithBDDMockito {

    @TestConfiguration
    @ComponentScan(includeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = HolidaysRepository.class))
    public class TestsConfig {}

    @Autowired
    private HolidaysRepository holidaysRepository;

    @Test
    @DatabaseSetup("classpath:data/repository/holidays-repository/" +
            "count-holidays-between.xml")
    public void shouldCountHolidaysBetweenTwoDatesCorrectly() {
        int expectedHolidaysCount = 10;
        int actualHolidaysCount = holidaysRepository.countHolidaysBetween("2016-03-10", "2017-08-10");

        Assert.assertEquals(expectedHolidaysCount, actualHolidaysCount);
    }
}
