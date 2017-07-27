package com.ihordev.holidays.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT LOGIN AS USERNAME, PASSWORD, 1 AS ENABLED FROM USERS WHERE LOGIN=?")
                .authoritiesByUsernameQuery("SELECT LOGIN AS USERNAME, 'ROLE_USER' FROM USERS WHERE LOGIN=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").defaultSuccessUrl("/holidays", true)
                        .and()
                        .logout().logoutSuccessUrl("/greetings")
                .and()
                .authorizeRequests()
                    .antMatchers("/holidays").hasRole("USER")
                    .anyRequest().permitAll();
    }
}
