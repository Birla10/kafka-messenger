package com.kafka.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.kafka.demo.service.XmlFileUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private XmlFileUserDetailsService xmlFileUserDetailsService;


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers("/login", "/api/produce/**", "/api/authenticate", "/ws/**",
								"http://localhost:3000", "http://localhost:3000/chatwindow")
						.permitAll() // Allow access to login page
						.anyRequest().authenticated() // All other paths require authentication
				);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	               .userDetailsService(xmlFileUserDetailsService)
	               .passwordEncoder(passwordEncoder())
	               .and()
	               .build();
	}

	@Bean
	UserDetailsService users() {
	    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	    PasswordEncoder encoder = passwordEncoder();
	    manager.createUser(User.withUsername("user1").password(encoder.encode("password")).roles("USER").build());
	    manager.createUser(User.withUsername("user2").password(encoder.encode("password")).roles("USER").build());
	    return manager;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}