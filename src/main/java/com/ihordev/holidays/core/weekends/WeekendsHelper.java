package com.ihordev.holidays.core.weekends;

import com.ihordev.holidays.core.util.Date;
import com.ihordev.holidays.core.util.DateUtils;

import static com.ihordev.holidays.core.util.DateUtils.DAYS_IN_WEEK;

/**
 * <p>A helper class that counts weekends between two dates.
 */
public final class WeekendsHelper {

    private WeekendsHelper() {
        throw new AssertionError("WeekendsHelper cannot be instantiated.");
    }

    /**
     * Counts number of weekends, that is saturdays and sundays between two dates.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return number of days
     */
    public static int countWeekendsBetween(Date startDate, Date endDate) {
        int daysBetween = DateUtils.getDaysBetween(startDate, endDate);
        int startDateWeekDay = startDate.getWeekDay();
        int endDateWeekDay = endDate.getWeekDay();
        
        int weekendsCount = 0;

        weekendsCount += (startDateWeekDay <= 6) ? 2 : (startDateWeekDay == 7 ? 1 : 0);
        weekendsCount += (daysBetween - (DAYS_IN_WEEK - startDateWeekDay)) / 7 * 2;
        weekendsCount += (endDateWeekDay == 6 ) ? 1 : 0;

        return weekendsCount;
    }
}
