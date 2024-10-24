package com.w2m.tvmedia.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig {

	private final JwtTokenFilter jwtTokenFilter;
	private final JwtAuthEntryPoint jwtAuthEntryPoint;

	public AppSecurityConfig(JwtTokenFilter jwtTokenFilter, JwtAuthEntryPoint jwtAuthEntryPoint) {
		super();
		this.jwtTokenFilter = jwtTokenFilter;
		this.jwtAuthEntryPoint = jwtAuthEntryPoint;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(cors -> cors.disable()).csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
				.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorize -> {
					authorize.requestMatchers("/api-docs").permitAll();
					authorize.requestMatchers("/swagger-ui.html").permitAll();
					authorize.requestMatchers("/api/mgmt/**").permitAll();
					authorize.requestMatchers("/api/v1/auth").permitAll();
					authorize.requestMatchers("/h2-console/**").permitAll();
					authorize.requestMatchers("/api/v1/kafka-messages").permitAll();
					authorize.anyRequest().authenticated();
				}).headers(httpSecurityHeadersConfigurer -> {
					httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig -> {
						frameOptionsConfig.disable();
					});
				}).httpBasic(Customizer.withDefaults());

		http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint));

		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}