package com.security.dkbt.config;

import com.security.dkbt.config.error.security.SecurityAccessDeniedHandler;
import com.security.dkbt.config.error.security.SecurityAuthEntryPoint;
import com.security.dkbt.config.jwt.JwtFilter;
import com.security.dkbt.config.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final SecurityAuthEntryPoint securityAuthEntryPoint;
	private final SecurityAccessDeniedHandler securityAccessDeniedHandler;

	@Bean
	JwtFilter jwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		return new JwtFilter(jwtUtil, userDetailsService);
	}
	
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter(
	        AuthenticationManager authenticationManager,
	        JwtUtil jwtUtil
	) {
	    return new JwtAuthenticationFilter(authenticationManager, jwtUtil);
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter,
			JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(ex -> ex.authenticationEntryPoint(securityAuthEntryPoint)
						.accessDeniedHandler(securityAccessDeniedHandler))
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())
				.addFilter(jwtAuthenticationFilter)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}