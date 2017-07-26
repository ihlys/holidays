package com.ihordev.holidays.domain;

import com.ihordev.holidays.core.util.Date;

public class Holiday {

    private Long id;
    private Date date;

    public Holiday(Long id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
