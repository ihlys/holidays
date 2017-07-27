package com.ihordev.holidays.core.weekends;

import com.ihordev.holidays.core.util.Date;
import org.junit.Assert;
import org.junit.Test;

public class WeekendsHelperTests {

    @Test
    public void shouldCountWeekendsBetweenTwoDatesCorrectly() {
        // expected results are generated with help of this site: https://www.timeanddate.com/date/workdays.html
        // be sure to check 'Include end date in calculation(1 day is added)'

        int expectedWeekendsBetween1 = 66;
        int expectedWeekendsBetween2 = 4312;

        Date startDate1 = Date.of("2016-12-09"); Date endDate1 = Date.of("2017-07-26");
        Date startDate2 = Date.of("1981-06-05"); Date endDate2 = Date.of("2022-09-25");

        int actualWeekendsBetween1 = WeekendsHelper.countWeekendsBetween(startDate1, endDate1);
        int actualWeekendsBetween2 = WeekendsHelper.countWeekendsBetween(startDate2, endDate2);

        Assert.assertEquals(expectedWeekendsBetween1, actualWeekendsBetween1);
        Assert.assertEquals(expectedWeekendsBetween2, actualWeekendsBetween2);
    }

}
