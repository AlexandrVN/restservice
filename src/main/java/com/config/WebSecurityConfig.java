package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //включаем авторизацию
                .authorizeRequests()
                    //пользователи, который приходят по этому пути имеют полный доступ
                    .antMatchers("/", "/registration").permitAll()
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

/*    @Bean
    @Override
    protected UserDetailsService userDetailsService() {

        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("u")
                        .password("1")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select username, password, active from user_db where username=?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from user_db u inner join user_role ur on u.id = ur.user_id where u.username=?");
        }
}
