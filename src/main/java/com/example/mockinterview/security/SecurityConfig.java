package com.example.mockinterview.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers("/api/auth/**").permitAll()

                        // H2 Console
                        .requestMatchers("/h2-console/**").permitAll()

                        // Static resources
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // H2 console fix
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}