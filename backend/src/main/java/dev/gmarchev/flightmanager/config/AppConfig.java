package dev.gmarchev.flightmanager.config;

import dev.gmarchev.flightmanager.security.JwtAuthenticationFilter;
import dev.gmarchev.flightmanager.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableRetry
@EnableConfigurationProperties({ AccountMapping.class })
@RequiredArgsConstructor
public class AppConfig {

	private final AuthenticationService authenticationService;

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		return authenticationService;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

		return config.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(authenticationService);
		authProvider.setPasswordEncoder(passwordEncoder);

		return authProvider;
	}

	@Bean
	SecurityFilterChain securityFilterChain(
			HttpSecurity http,
			AuthenticationProvider authenticationProvider,
			JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

		return http
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/h2-console/**").permitAll();
//					auth.requestMatchers("/test/**").hasRole("ADMIN");
					auth.requestMatchers(
							"/auth/login", "/auth/login/", "/auth/refresh-token", "/auth/refresh-token/")
							.permitAll();
					auth.requestMatchers("/employee-accounts/**").hasRole("ADMIN");
					auth.requestMatchers(HttpMethod.POST, "/flights").hasRole("ADMIN");
					auth.requestMatchers("/flights").permitAll();
					auth.anyRequest().authenticated();
					//					auth.anyRequest().permitAll();
				})
				.headers(headers -> headers.frameOptions().disable()) // Disable X-Frame-Options header for H2 console )
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) // disable serverside session for JWT auth
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(authenticationProvider)
				// TODO
//				.logout(logout ->
//						logout.logoutUrl("/api/auth/logout")
//								.addLogoutHandler(logoutHandler)
//								.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//				)
				.build();
	}
}
