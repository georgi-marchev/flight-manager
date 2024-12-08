package dev.gmarchev.flightmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppConfig {

	@Bean
	public PasswordEncoder createPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// TODO: allow all requests until auth is implemented
		return http
				.authorizeHttpRequests(auth -> {
//					auth.requestMatchers("/h2-console/**").permitAll();
//					auth.requestMatchers("/css/**","/js/**").permitAll();
//					auth.anyRequest().authenticated();
					auth.anyRequest().permitAll();
				})
				.headers(headers -> headers .frameOptions().disable()) // Disable X-Frame-Options header for H2 console )
				.csrf(AbstractHttpConfigurer::disable)
				.build();
	}
}
