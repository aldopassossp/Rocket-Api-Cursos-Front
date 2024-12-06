package com.aldopassos.cursos_front.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests(auth -> {
                    auth.requestMatchers("/api/login").permitAll()
                //            .requestMatchers("/curso/create").permitAll()
                            .requestMatchers("/api/signIn").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> form.loginPage("/api/login"));

        return http.build();
    }
}

