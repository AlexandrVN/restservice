package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //включаем авторизацию
                .authorizeRequests()
                    //пользователи, который приходят по этому пути имеют полный доступ
                    .antMatchers("/").permitAll()
                    //для других запросов требуем авторизацию
                    .anyRequest().authenticated()
                .and()
                    //включаем форму для авторизации
                    .formLogin()
                    //путь, по которому находится форма авторизации
                    .loginPage("/login")
                    //разрешение пользоваться этим всем, кто вошел сюда
                    .permitAll()
                .and()
                    //включаем logout и разрешаем пользоваться всем
                    .logout()
                    .permitAll();

    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {

        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("u")
                        .password("1")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }


}
