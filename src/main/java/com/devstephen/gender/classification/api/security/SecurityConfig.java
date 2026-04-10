package com.devstephen.gender.classification.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
  private SecurityFilterChain securityFilterChain(HttpSecurity http){
    http.authorizeHttpRequests(request ->
        request.requestMatchers("")
            .permitAll()
            .anyRequest().authenticated())
  }

}
