package com.ihordev.holidays.web;

import com.ihordev.holidays.service.HolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HolidaysController {

    @Autowired
    private HolidaysService holidaysService;

    @GetMapping("/holidays")
    public String holidaysView() {
        return "holidays";
    }

    @GetMapping(value = "/holidays", params = {"startDate", "endDate"})
    public String calculatedHolidays(@RequestParam("startDate") String startDate,
                                     @RequestParam("endDate") String endDate,
                                     Model model) {
        int holidaysCount = holidaysService.getHolidaysBetween(startDate, endDate);
        model.addAttribute("holidaysCount", holidaysCount);
        return "holidays";
    }

    @GetMapping(value = "/holidays", params = {"startDate", "endDate"},
            headers = "X-Requested-With=XMLHttpRequest", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String calculatedHolidays(@RequestParam("startDate") String startDate,
                                     @RequestParam("endDate") String endDate) {
        int holidaysCount = holidaysService.getHolidaysBetween(startDate, endDate);
        return String.valueOf(holidaysCount);
    }
}
