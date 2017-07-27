package com.ihordev.holidays.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/holidays").setViewName("holidays");
        registry.addViewController("/greetings").setViewName("greetings");
        registry.addViewController("/login").setViewName("login");
    }
}
