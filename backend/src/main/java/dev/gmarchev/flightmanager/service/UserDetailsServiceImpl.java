package dev.gmarchev.flightmanager.service;

import java.util.Set;
import java.util.stream.Collectors;

import dev.gmarchev.flightmanager.model.Account;
import dev.gmarchev.flightmanager.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
/**
 * TODO: Improve by adding access and refresh tokens to the database
 * When new tokens are issued on login or refresh, invalidate old tokens.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Account account = accountRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));

		// Convert the roles to authorities
		Set<SimpleGrantedAuthority> authorities = account.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
				.collect(Collectors.toSet());

		// Return a Spring Security User object with the account's password and roles
		return new User(account.getUsername(), account.getPassword(), authorities);
	}
}
