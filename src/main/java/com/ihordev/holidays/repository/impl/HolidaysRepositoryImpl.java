package com.ihordev.holidays.repository.impl;

import com.ihordev.holidays.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Types;

@Repository
public class HolidaysRepositoryImpl implements HolidaysRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public int countHolidaysBetween(String startDate, String endDate) {
        SimpleJdbcCall call = new SimpleJdbcCall(dataSource);
        call.withCatalogName("pkg_holidays");
        call.withProcedureName("count_holidays_between");
        call.addDeclaredParameter(new SqlOutParameter("p_holidays_count", Types.INTEGER));
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("p_start_date", Date.valueOf(startDate));
        in.addValue("p_end_date", Date.valueOf(endDate));
        return (int) call.execute(in).get("p_holidays_count");
    }
}
