package dev.gmarchev.flightmanager.controller;

import dev.gmarchev.flightmanager.dto.AccountRequest;
import dev.gmarchev.flightmanager.repository.RoleRepository;
import dev.gmarchev.flightmanager.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

	// TODO: validate
	@PostMapping("/create-employee")
	public ResponseEntity<String> create(@RequestBody AccountRequest accountRequest) {

		// TODO: implement

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Account created successfully");
	}
}
