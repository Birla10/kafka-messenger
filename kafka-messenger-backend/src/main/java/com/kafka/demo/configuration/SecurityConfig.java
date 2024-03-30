package com.kafka.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/login"
                    		,"/api/messages/produce/**"
                    		,"/ws/**"
                    		,"http://localhost:3000"
                    		,"http://localhost:3000/chatwindow").permitAll() // Allow access to login page
                    .anyRequest().authenticated()      // All other paths require authentication
            );
        
        return http.build();
    }

    @Bean
    UserDetailsService users() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user1").password("password").roles("USER").build());
        manager.createUser(User.withUsername("user2").password("password").roles("USER").build());
        return manager;
    }
}
