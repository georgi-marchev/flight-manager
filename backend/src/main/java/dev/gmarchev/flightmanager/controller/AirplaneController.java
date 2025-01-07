package dev.gmarchev.flightmanager.controller;

import java.util.List;

import dev.gmarchev.flightmanager.dto.airplane.AirplaneCreateRequest;
import dev.gmarchev.flightmanager.dto.airplane.AirplaneResponse;
import dev.gmarchev.flightmanager.service.AirplaneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/airplanes")
@RequiredArgsConstructor
public class AirplaneController {

	private final AirplaneService airplaneService;

	@GetMapping
	public ResponseEntity<List<AirplaneResponse>> getAirplanes() {

		return ResponseEntity.ok(airplaneService.getAirplanes());
	}

	@PostMapping
	public ResponseEntity<Object> createAirplane(@RequestBody @Valid AirplaneCreateRequest airplaneCreateRequest) {

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new Object() {
					public final Long airplaneId = airplaneService.createAirplane(airplaneCreateRequest);
				});
	}
}
