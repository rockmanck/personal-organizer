package com.jediorganizer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for Jedi Organizer application.
 * 
 * This configuration sets up basic security while allowing public access 
 * to health check endpoints and API documentation.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configure the security filter chain.
     * 
     * @param http the HttpSecurity configuration
     * @return the SecurityFilterChain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Allow public access to health check endpoints
                .requestMatchers("/health/**", "/actuator/**").permitAll()
                // Allow public access to API documentation
                .requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // For now, allow all requests (will be secured later)
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF for API endpoints
            .cors(cors -> cors.disable()); // Disable CORS for now

        return http.build();
    }
}
