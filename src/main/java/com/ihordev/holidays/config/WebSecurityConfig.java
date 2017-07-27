package com.ihordev.holidays.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
      		.inMemoryAuthentication()
      		.withUser("user").password("password").roles("USER");
/*
        auth.jdbcAuthentication()
                .authoritiesByUsernameQuery("SELECT LOGIN, PASSWORD, TRUE " +
                                            "    FROM USERS               " +
                                            "    WHERE LOGIN = ?          ")
                .authoritiesByUsernameQuery("SELECT LOGIN, 'ROLE_USER'    " +
                                            "    FROM USERS               " +
                                            "    WHERE LOGIN = ?          ")
                .passwordEncoder(passwordEncoder());*/
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
