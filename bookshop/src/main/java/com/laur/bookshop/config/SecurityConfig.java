package com.laur.bookshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless APIs (optional, depending on your needs)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/app_users/login").permitAll() // Allow unauthenticated access to login
                        .requestMatchers("/app_users/add").permitAll() // Allow unauthenticated access to add user (if intended)
                        .requestMatchers("/app_users/all").permitAll() // Require authentication for getting all users
                        .requestMatchers("/app_users/delete").authenticated() // Require authentication for deleting users
                        .requestMatchers("/app_users/edit").authenticated()   // Require authentication for editing users
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
