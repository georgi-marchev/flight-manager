package dev.gmarchev.flightmanager.controller;

import dev.gmarchev.flightmanager.dto.FlightCreateRequest;
import dev.gmarchev.flightmanager.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

	private final FlightService flightService;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody @Valid FlightCreateRequest flightCreateRequest) {

		try	{

			flightService.createFlight(flightCreateRequest);

		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Flight created successfully");
	}
}
