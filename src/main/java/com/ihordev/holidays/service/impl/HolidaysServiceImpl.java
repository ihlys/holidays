package com.ihordev.holidays.service.impl;

import com.ihordev.holidays.core.util.Date;
import com.ihordev.holidays.core.weekends.WeekendsHelper;
import com.ihordev.holidays.repository.HolidaysRepository;
import com.ihordev.holidays.service.HolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidaysServiceImpl implements HolidaysService {

    private HolidaysRepository holidaysRepository;

    @Autowired
    public void setHolidaysRepository(HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }

    @Override
    public int getHolidaysBetween(String startDate, String endDate) {
        return holidaysRepository.countHolidaysBetween(startDate, endDate) +
                WeekendsHelper.countWeekendsBetween(Date.of(startDate), Date.of(endDate));
    }
}
