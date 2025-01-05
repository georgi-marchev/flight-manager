package dev.gmarchev.flightmanager.config;

import java.util.Arrays;

import dev.gmarchev.flightmanager.security.JwtAuthenticationFilter;
import dev.gmarchev.flightmanager.service.UserDetailsServiceImpl;
import jakarta.servlet.DispatcherType;
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
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableRetry
@EnableConfigurationProperties({ AccountMapping.class })
@RequiredArgsConstructor
public class AppConfig {

	private final UserDetailsServiceImpl userDetailsServiceImpl;

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		return userDetailsServiceImpl;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

		return config.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsServiceImpl);
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
					auth.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll();
					auth.requestMatchers("/api/auth/login", "/api/auth/refresh-token").permitAll();
					auth.requestMatchers("/api/employee-accounts/**").hasRole("ADMIN");
					auth.requestMatchers(HttpMethod.GET, "/api/flights").permitAll();
					auth.requestMatchers(HttpMethod.POST, "/api/flights").hasRole("ADMIN");
					auth.requestMatchers(HttpMethod.GET, "/api/flights/{id}").permitAll();
					auth.requestMatchers(HttpMethod.POST, "/api/reservations").permitAll();
					auth.requestMatchers( "/api/pilots").hasRole("ADMIN");
					auth.requestMatchers( "/api/airplanes").hasRole("ADMIN");
					auth.requestMatchers( "/api/airplane-models").hasRole("ADMIN");
					auth.requestMatchers( "/api/locations").hasRole("ADMIN");
					auth.requestMatchers("/api/**").authenticated();
					auth.anyRequest().permitAll();
				})
				// Set X-Frame-Options header to same origin for H2 console
				.headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
				.csrf(AbstractHttpConfigurer::disable)
				// TODO: Configure properly for production
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration configuration = new CorsConfiguration();
					configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
					configuration.setAllowedMethods(Arrays.asList("*"));
					configuration.setAllowedHeaders(Arrays.asList("*"));
					configuration.setAllowCredentials(true);
					return configuration;
				}))
				.sessionManagement(
						session -> session.sessionCreationPolicy(STATELESS)) // disable serverside session for JWT auth
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(authenticationProvider)
				.build();
	}
}
