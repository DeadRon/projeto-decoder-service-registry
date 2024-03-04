package com.ead.serviceregistry.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${ead.serviceRegistry.username}")
    private String userName;

    @Value("${ead.serviceRegistry.password}")
    private String password;

    /*
    define que todas as requisições precisam ser autenticadas
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin();
    }

    /*
    define o tipo de autenticação
    */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(userName)
                .password(passwordEncoder().encode(password))
                .roles("ADMIN");
    }

    /*
    cria um bean PasswordEncoder
    */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}